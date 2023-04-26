# -*-coding: utf-8 -*-
"""
    @Author : PKing
    @E-mail : 390737991@qq.com
    @Date   : 2022-12-30 10:40:48
    @Brief  :
"""
import torch
import numpy as np
from core.dataloader import plate_dataset


def load_dataset(data_type,
                 filename,
                 transform,
                 class_name=None,
                 shuffle=True,
                 resample=True,
                 check=False,
                 phase="train",
                 use_rgb=False,
                 **kwargs):
    """
    :param data_type:
    :param filename:
    :param transform:
    :param class_name:
    :param shuffle:
    :param check:
    :param phase:
    :param use_rgb:
    :param kwargs:
    :return:
    """
    if data_type.lower() == "Plate".lower():
        dataset = plate_dataset.PlateDataset(data_root=filename,
                                             class_name=class_name,
                                             transform=transform,
                                             resample=resample,
                                             shuffle=shuffle)
    else:
        raise Exception("Error:{}".format(data_type))
    return dataset


class Collation(object):
    """
    Custom collate fn for dealing with batches of images that have a different
    number of associated object annotations.
    """

    def __init__(self, stacks={}):
        """
        :param stacks: 需要堆叠一个batch-size的数据，通过Key指定
                      如 stacks = {'image': True, 'target': True, 'points': False}
        """
        self.stacks = stacks

    def __call__(self, batch):
        if isinstance(batch[0], dict):
            return self.collate_for_dict(batch)
        elif isinstance(batch[0], tuple) or isinstance(batch[0], list):
            return self.collate_for_list_tuple(batch)
        else:
            return batch

    def collate_for_dict(self, batch):
        """Dataset返回的Dict格式的数据"""
        # 初始化outputs和lengths
        data = batch[0]
        outputs = {k: list() for k, v in data.items()}
        assert isinstance(data, dict), "batch's item must be Dict"
        if not self.stacks: self.stacks = {k: True for k, v in data.items()}
        # print("count:{},stacks:{}".format(self.count, self.stacks))
        # 将batch相同Key合并在同一个list中
        for data in batch:
            for k, v in data.items():
                if isinstance(v, list) or isinstance(v, tuple):
                    v = np.asarray(v)
                if isinstance(v, np.ndarray):
                    outputs[k].append(torch.from_numpy(v))
                else:
                    outputs[k].append(v)
        # 仅当维度相同时，才进行stack
        for k, v in outputs.items():
            try:
                if self.stacks[k]: outputs[k] = torch.stack(outputs[k])
            except:
                self.stacks[k] = False
        return outputs

    def collate_for_list_tuple(self, batch):
        """Dataset返回的List或Tuple格式的数据"""
        # 初始化outputs和lengths
        data = batch[0]
        outputs = [[] for k, v in enumerate(data)]
        assert isinstance(data, tuple) or isinstance(data, list), "batch's item must be List or tuple"
        if not self.stacks: self.stacks = {k: True for k, v in enumerate(data)}
        # 将batch总，相同Key合并在同一个list中
        for data in batch:
            for k, v in enumerate(data):
                if isinstance(v, list) or isinstance(v, tuple):
                    v = np.asarray(v)
                if isinstance(v, np.ndarray):
                    outputs[k].append(torch.from_numpy(v))
                else:
                    outputs[k].append(v)
        # 仅当维度相同时，才进行stack
        for k, v in enumerate(outputs):
            try:
                if self.stacks[k]:  outputs[k] = torch.stack(outputs[k])
            except:
                self.stacks[k] = False
        return outputs
