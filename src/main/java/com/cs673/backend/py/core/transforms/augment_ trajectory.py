# -*-coding: utf-8 -*-
"""
    @Author : panjq
    @E-mail : pan_jinquan@163.com
    @Date   : 2022-01-13 11:11:31
"""
import math
import numpy as np
import random
import torch
import cv2
import json
from torchvision import transforms
from typing import List, Tuple, Dict


class RandomShift(object):
    def __init__(self, seq_range=(0, 173), p=1.0):
        """
        :param seq_range:  输入序列sequence最大最小范围
        :param margin_rate: 偏移大小
        :param p: 概率
        """
        self.seq_range = seq_range
        self.p = p

    def random_offset(self, box, seq_range):
        xmin, ymin, xmax, ymax = box
        tw = xmax - xmin
        th = ymax - ymin
        x = int(random.uniform(seq_range[0] - xmin, seq_range[1] - tw - xmin))
        y = int(random.uniform(seq_range[0] - ymin, seq_range[1] - th - ymin))
        return (x, y)

    def __call__(self, x):
        if random.random() < self.p:
            xmin = np.min(x[:, 0])
            ymin = np.min(x[:, 1])
            xmax = np.max(x[:, 0])
            ymax = np.max(x[:, 1])
            box = [xmin, ymin, xmax, ymax]
            offset = self.random_offset(box, self.seq_range)
            x = x + offset
        return x


class RandomHorizontalFlip(object):
    def __init__(self, seq_width=173, p=0.5):
        """
        :param seq_width:  随机水平翻转
        :param p: 概率
        """
        self.seq_width = seq_width
        self.p = p

    def __call__(self, x):
        if random.random() < self.p:
            x[:, 0] = self.seq_width - x[:, 0]
        return x
