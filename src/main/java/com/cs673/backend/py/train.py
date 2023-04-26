# -*-coding: utf-8 -*-
"""
    @Author : PKing
    @E-mail : 390737991@qq.com
    @Date   : 2022-12-30 10:40:48
    @Brief  :
"""
import os
import argparse
import torch
import core.utils.utils as utils
import torch.nn as nn
from torch.utils.data import DataLoader
from core.models import build_model
from core.transforms import build_transform
from core.dataloader import plate_dataset, build_dataset
from core.utils import torch_tools
from tensorboardX import SummaryWriter
from pybaseutils import file_utils, config_utils, log
from tqdm import tqdm


class Trainer(object):
    def __init__(self, cfg):
        time = file_utils.get_time()
        flag = [n for n in [cfg.net_type, cfg.flag, time] if n]
        cfg.work_dir = os.path.join(cfg.work_dir, "_".join(flag))
        cfg.model_root = os.path.join(cfg.work_dir, "model")
        cfg.log_root = os.path.join(cfg.work_dir, "log")
        file_utils.create_dir(cfg.work_dir)
        file_utils.create_dir(cfg.model_root)
        file_utils.create_dir(cfg.log_root)
        file_utils.copy_file_to_dir(cfg.config_file, cfg.work_dir)
        self.cfg = cfg
        # 运行设备
        self.gpu_id = cfg.gpu_id
        self.device = torch.device("cuda:{}".format(self.cfg.gpu_id[0]) if torch.cuda.is_available() else "cpu")
        # 设置Log打印信息
        self.logger = log.set_logger(level="debug", logfile=os.path.join(cfg.log_root, "train.log"))
        self.writer = SummaryWriter(self.cfg.log_root)
        # 加载训练和测试数据
        self.train_loader = self.build_train_loader()
        self.test_loader = self.build_test_loader()
        self.num_classes = self.cfg.num_classes
        self.class_name = self.cfg.class_name
        # 构建模型
        self.model = self.build_model()
        # 构建criterion函数
        self.criterion = self.build_criterion()
        # 构建optimizer
        self.optimizer = self.build_optimizer()
        # 构建lr_scheduler
        self.scheduler = torch.optim.lr_scheduler.MultiStepLR(self.optimizer, self.cfg.milestones)
        # 打印信息
        self.num_samples = len(self.train_loader.sampler)
        self.logger.info("=" * 60)
        self.logger.info("work_dir          :{}".format(cfg.work_dir))
        self.logger.info("config_file       :{}".format(cfg.config_file))
        self.logger.info("gpu_id            :{}".format(cfg.gpu_id))
        self.logger.info("main device       :{}".format(self.device))
        self.logger.info("num_samples(train):{}".format(self.num_samples))
        self.logger.info("num_classes       :{}".format(self.num_classes))
        self.logger.info("class_name        :{}".format(self.class_name))
        self.logger.info("=" * 60)

    def build_model(self):
        """构建模型"""
        model = build_model.get_models(net_type=self.cfg.net_type, num_classes=self.cfg.num_classes,
                                       input_size=self.cfg.input_size, is_train=True)
        if isinstance(self.cfg.pretrained, str) and os.path.exists(self.cfg.pretrained):
            check_point = torch.load(self.cfg.pretrained, map_location="cpu")
            model_state = check_point['state_dict'] if "state_dict" in check_point else check_point
            model = torch_tools.load_pretrained_model(model, model_state)
            self.logger.info("load pretrained model:{}".format(self.cfg.pretrained))
        model = model.to(self.device)
        model = nn.DataParallel(model, device_ids=self.gpu_id, output_device=self.device)
        return model

    def build_optimizer(self, ):
        """构建优化器"""
        if self.cfg.optim_type.lower() == "SGD".lower():
            optimizer = torch.optim.SGD(params=filter(lambda p: p.requires_grad, self.model.parameters()),
                                        lr=self.cfg.lr,
                                        momentum=self.cfg.momentum,
                                        weight_decay=self.cfg.weight_decay,
                                        nesterov=False)
        elif self.cfg.optim_type.lower() == "Adam".lower():
            optimizer = torch.optim.Adam(params=filter(lambda p: p.requires_grad, self.model.parameters()),
                                         lr=self.cfg.lr,
                                         weight_decay=self.cfg.weight_decay)
        elif self.cfg.optim_type.lower() == "AdamW".lower():
            optimizer = torch.optim.AdamW(params=filter(lambda p: p.requires_grad, self.model.parameters()),
                                          lr=self.cfg.lr,
                                          weight_decay=self.cfg.weight_decay)
        else:
            optimizer = None
        return optimizer

    def build_train_loader(self):
        """构建训练数据"""
        transform = build_transform.image_transform(input_size=self.cfg.input_size,
                                                    rgb_mean=self.cfg.rgb_mean,
                                                    rgb_std=self.cfg.rgb_std,
                                                    trans_type=self.cfg.train_transform)

        dataset = plate_dataset.PlateDataset(data_root=self.cfg.train_data,
                                             class_name=self.cfg.class_name,
                                             transform=transform,
                                             resample=self.cfg.resample,
                                             shuffle=True)
        dataloader = DataLoader(dataset=dataset,
                                batch_size=self.cfg.batch_size,
                                shuffle=True,
                                num_workers=self.cfg.num_workers,
                                pin_memory=True,
                                collate_fn=build_dataset.Collation()
                                )
        self.cfg.num_classes = dataset.num_classes
        self.cfg.class_name = dataset.class_name
        return dataloader

    def build_test_loader(self):
        """构建测试数据 """
        transform = build_transform.image_transform(input_size=self.cfg.input_size,
                                                    rgb_mean=self.cfg.rgb_mean,
                                                    rgb_std=self.cfg.rgb_std,
                                                    trans_type=self.cfg.test_transform)

        dataset = plate_dataset.PlateDataset(data_root=self.cfg.test_data,
                                             class_name=self.cfg.class_name,
                                             transform=transform,
                                             resample=False,
                                             shuffle=False)
        dataloader = DataLoader(dataset=dataset,
                                batch_size=self.cfg.batch_size,
                                shuffle=False,
                                num_workers=self.cfg.num_workers,
                                pin_memory=False,
                                collate_fn=build_dataset.Collation()
                                )
        return dataloader

    def build_criterion(self):
        """构建损失函数"""
        criterion = torch.nn.CTCLoss(blank=0)
        return criterion

    def train(self, epoch):
        """
        :param epoch:
        :return:
        """
        losses = utils.AverageMeter()
        accuracy = utils.AverageMeter()
        self.model.train()
        log_step = max(len(self.train_loader) // self.cfg.log_freq, 1)
        for step, (inputs, names, labels) in enumerate(tqdm(self.train_loader)):
            inputs = inputs.to(self.device)
            outputs = self.model(inputs)  # outputs=(序列的长度T=21, batch-size, num_class)
            targets, targets_length = utils.get_data_targets(labels)
            inputs_length = torch.IntTensor([outputs.size(0)] * inputs.size(0))  # 输出序列长度T列表
            loss = self.criterion(outputs, targets, inputs_length, targets_length)
            #
            self.optimizer.zero_grad()
            loss.backward()
            self.optimizer.step()
            _, outputs = outputs.max(2)
            pred_label = utils.get_pred_labels(outputs.transpose(1, 0))
            pred_name = utils.decode_name(pred_label, class_name=self.class_name)
            acc = utils.get_accuracy(pred_name, names)
            losses.update(loss.item(), inputs.size(0))
            accuracy.update(acc, inputs.size(0))
            if step % log_step == 0:
                lr = self.scheduler.get_last_lr()[0]  # 获得当前学习率
                msg = "train {:0=5d}/epoch:{:0=3d},lr:{:3.4f},loss:{:3.4f}，acc:{:3.4f}".format(step, epoch, lr,
                                                                                               losses.avg, accuracy.avg)
                self.logger.info(msg)
        self.writer.add_scalar("train-loss", losses.avg, epoch)
        self.writer.add_scalar("train-accuracy", accuracy.avg, epoch)
        self.logger.info("-------------------------" * 4)

    def test(self, epoch):
        """
        :param epoch:
        :return:
        """
        losses = utils.AverageMeter()
        self.model.eval()
        pd_preds = []
        gt_preds = []
        with torch.no_grad():
            for step, (inputs, names, labels) in enumerate(tqdm(self.test_loader)):
                inputs = inputs.to(self.device)
                outputs = self.model(inputs)
                targets, targets_length = utils.get_data_targets(labels)
                inputs_length = torch.IntTensor([outputs.size(0)] * inputs.size(0))  # 输出序列长度T列表
                loss = self.criterion(outputs, targets, inputs_length, targets_length)
                losses.update(loss.item(), inputs.size(0))
                _, outputs = outputs.max(2)
                pred_label = utils.get_pred_labels(outputs.transpose(1, 0))
                pred_name = utils.decode_name(pred_label, class_name=self.class_name)
                pd_preds += pred_name
                gt_preds += names
        lr = self.scheduler.get_last_lr()[0]  # 获得当前学习率
        acc = utils.get_accuracy(pd_preds, gt_preds)
        self.writer.add_scalar("test-loss", losses.avg, epoch)
        self.writer.add_scalar("test-accuracy", acc, epoch)
        self.logger.info("test  epoch:{:0=3d},lr:{:3.4f},loss:{:3.4f},acc:{:3.4f}".format(epoch, lr, losses.avg, acc))
        self.logger.info("-------------------------" * 4)
        return acc

    def run(self):
        """
        :return:
        """
        self.max_acc = 0.0
        for epoch in range(0, self.cfg.num_epochs):
            self.train(epoch)
            test_acc = self.test(epoch)
            self.scheduler.step()
            self.save_model(self.cfg.model_root, test_acc, epoch)
            lr = self.scheduler.get_last_lr()[0]  # 获得当前学习率
            self.writer.add_scalar("lr", lr, epoch)

    def save_model(self, model_root, value, epoch):
        """
        :param model_root:
        :param value:
        :param epoch:
        :return:
        """
        # 保存最优的模型
        state_dict = {
            "cfg": self.cfg,
            "state_dict": self.model.module.state_dict(),
            "epoch": epoch + 1,
            # "optimizer": optimizer.state_dict(),
            # "lr_scheduler": lr_scheduler.state_dict(),
        }
        if value >= self.max_acc:
            self.max_acc = value
            model_file = os.path.join(model_root, "best_model_{:0=3d}_{:.4f}.pth".format(epoch, value))
            file_utils.remove_prefix_files(model_root, "best_model_*")
            torch.save(state_dict, model_file)
            self.logger.info("save best   model file:{}".format(model_file))
        # 保存最新的模型
        name = "model_{:0=3d}_{:.4f}.pth".format(epoch, value)
        model_file = os.path.join(model_root, "latest_{}".format(name))
        file_utils.remove_prefix_files(model_root, "latest_*")
        torch.save(state_dict, model_file)
        self.logger.info("save latest model file:{}".format(model_file))
        self.logger.info("-------------------------" * 4)


def parse_arg():
    # config_file = "configs/config_lprnet.yaml"
    config_file = "configs/config_crnn.yaml"
    # config_file = "configs/config_platenet.yaml"
    parser = argparse.ArgumentParser(description="Training Pipeline")
    parser.add_argument("-c", "--config_file", help="configs file", default=config_file, type=str)
    parser.add_argument('--polyaxon', action='store_true', help='polyaxon', default=False)
    cfg = config_utils.parser_config(parser.parse_args(), cfg_updata=True)
    if cfg.polyaxon:
        from core.utils.rsync_data import get_polyaxon_dataroot, get_polyaxon_output
        cfg.gpu_id = list(range(len(cfg.gpu_id)))
        cfg.train_data = get_polyaxon_dataroot(dir=cfg.train_data)
        cfg.test_data = get_polyaxon_dataroot(dir=cfg.test_data)
        cfg.work_dir = get_polyaxon_output(cfg.work_dir)
        # if isinstance(cfg.class_name, str): cfg.class_name = get_polyaxon_dataroot(cfg.class_name)
    return cfg


if __name__ == '__main__':
    t = Trainer(cfg=parse_arg())
    t.run()
