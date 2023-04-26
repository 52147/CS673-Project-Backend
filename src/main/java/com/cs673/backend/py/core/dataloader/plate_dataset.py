# -*-coding: utf-8 -*-
"""
    @Author : PKing
    @E-mail : 390737991@qq.com
    @Date   : 2022-12-30 10:40:48
    @Brief  :
"""
import os
import cv2
import torch
import random
import numpy as np
import torch.utils.data as data
from pybaseutils import file_utils, image_utils, json_utils
from core.dataloader import balanced_classes
from core.dataloader import data_resample

PLATE_TABLE = "#京沪津渝冀晋蒙辽吉黑苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云藏陕甘青宁新学警港澳挂使领民航危0123456789ABCDEFGHJKLMNPQRSTUVWXYZ险品"


class PlateDataset(data.Dataset):
    def __init__(self, data_root, class_name="", transform=None, resample=False, shuffle=False, disp=False):
        """
        :param data_root: 数据路径
        :param class_name: 类别文件
        :param transform:  数据transform
        :param shuffle:
        """
        self.resample = resample
        self.transform = transform
        # 解析类别
        if not class_name: class_name = [n for n in PLATE_TABLE]
        self.class_name, self.class_dict = file_utils.parser_classes(class_name)
        self.item_list = self.get_item_list(data_root, shuffle=shuffle)
        if self.resample:
            self.data_resample = data_resample.DataResample(self.item_list,
                                                            label_index=1,
                                                            shuffle=shuffle,
                                                            disp=disp)
            self.item_list = self.data_resample.update(True)
            class_count = self.data_resample.class_count  # resample前，每个类别的分布
            # for n in self.class_name: class_count[n] = class_count[n] if n in class_count else 0
            balance_nums = self.data_resample.balance_nums  # resample后，每个类别的分布
            json_utils.write_json_path("./province_count.json", class_count)
            json_utils.write_json_path("./province_count_balance.json", balance_nums)
            print("use resample and province_count file in PWD")
        self.num_classes = len(self.class_name)
        self.num_samples = len(self.item_list)
        print("class_dict:{}".format(self.class_dict))
        print("have data :{}".format(len(self.item_list)))

    def get_item_list(self, data_root, shuffle=False):
        if isinstance(data_root, str):
            data_root = [data_root]
        item_list = []
        for i, dir in enumerate(data_root):
            item = file_utils.get_files_lists(dir)
            print("loading dataset from:{},have:{}".format(dir, len(item)))
            item_list += item
        item_list = [[file, os.path.basename(file)[0]] for file in item_list]
        if shuffle:
            random.seed(100)
            random.shuffle(item_list)
        print("have dataset :{}".format(len(item_list)))
        return item_list

    def __len__(self):
        return len(self.item_list)

    def __len__(self):
        if self.resample:
            self.item_list = self.data_resample.update(True)
        self.num_samples = len(self.item_list)
        return self.num_samples

    def __getitem__(self, index):
        image_file, province = self.item_list[index]
        name = os.path.basename(image_file).split("_")[0]
        image = self.read_image(image_file)
        if self.transform:
            image = self.transform(image.copy())
        label = [self.class_dict[n] for n in name]
        return image, name, label

    @staticmethod
    def read_image(image_file):
        # 读取中文路径的图片
        bgr = cv2.imdecode(np.fromfile(image_file, dtype=np.uint8), -1)  # BGR
        return bgr


if __name__ == '__main__':
    from core.transforms.build_transform import image_transform

    data_root = "/home/dm/nasdata/dataset/csdn/plate/dataset/CCPD2020-voc/train/plates"
    input_size = [168, 48]
    rgb_mean = [0., 0., 0.]
    rgb_std = [1., 1., 1.]
    transform = image_transform(input_size=input_size,
                                rgb_mean=rgb_mean,
                                rgb_std=rgb_std,
                                trans_type="train")
    dataset = PlateDataset(data_root=data_root, transform=transform, resample=True)
    for i in range(len(dataset)):
        image, name, label = dataset.__getitem__(1)
        print("name:{},label:{}".format(name, label))
        image = np.array(image, dtype=np.float32)
        image = image.transpose(1, 2, 0)
        image_utils.cv_show_image("image", image)
