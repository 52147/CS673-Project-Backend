# -*-coding: utf-8 -*-
"""
    @Author : panjq
    @E-mail : pan_jinquan@163.com
    @Date   : 2021-08-02 14:33:33
"""
import numbers
import random
import PIL.Image as Image
import numpy as np
from torchvision import transforms
from core.transforms import augment_image


def image_transform(input_size, rgb_mean=[0.5, 0.5, 0.5], rgb_std=[0.5, 0.5, 0.5], trans_type="train", bg_dir=""):
    """
    不推荐使用：RandomResizedCrop(input_size), # bug:目标容易被crop掉
    :param input_size: [w,h]
    :param rgb_mean:
    :param rgb_std:
    :param trans_type:
    :return::
    """

    if trans_type == "train":
        transform = transforms.Compose([
            transforms.ToPILImage(),
            transforms.Resize([int(115 * input_size[1] / 112), int(115 * input_size[0] / 112)]),
            # transforms.RandomHorizontalFlip(),  # 随机左右翻转
            # transforms.RandomVerticalFlip(), # 随机上下翻转
            # augment_image.RandomNoise(p=0.3),
            transforms.RandomPerspective(distortion_scale=0.2, p=0.5),
            augment_image.RandomRotation(degrees=5, p=0.5),
            transforms.RandomCrop([input_size[1], input_size[0]]),
            augment_image.RandomColorJitter(brightness=0.5, contrast=0.5, saturation=0.5, hue=0.1),
            transforms.ToTensor(),
            transforms.Normalize(mean=rgb_mean, std=rgb_std),
        ])
    elif trans_type == "val" or trans_type == "test":
        transform = transforms.Compose([
            transforms.ToPILImage(),
            # transforms.Resize([int(115 * input_size[1] / 112), int(115 * input_size[0] / 112)]),
            # transforms.CenterCrop([input_size[1], input_size[0]]),
            transforms.Resize(size=(input_size[1], input_size[0])),
            transforms.ToTensor(),
            transforms.Normalize(mean=rgb_mean, std=rgb_std),
        ])
    elif trans_type == "demo":
        transform = transforms.Compose([
            # augment_image.RandomImageFusion(p=1.0, bg_dir=bg_dir),
            transforms.ToPILImage(),
            transforms.Resize([int(115 * input_size[1] / 112), int(115 * input_size[0] / 112)]),
            # transforms.CenterCrop([input_size[1], input_size[0]]),
            transforms.RandomPerspective(distortion_scale=0.2, p=0.5),
            # augment_image.RandomCenterScale(),
            # augment_image.RandomColorJitter(brightness=0.5, contrast=0.5, saturation=0.5, hue=0.2),
            # augment_image.SwapChannels(),
            # augment_image.RandomSaltPepperNoise(p=1.0),
            # augment_image.RandomNoise(p=1.0),
            # augment_image.RandomGaussianNoise(p=1.0),
            transforms.ToTensor(),
            transforms.Normalize(mean=rgb_mean, std=rgb_std),
            # transforms.RandomErasing(),
        ])
    else:
        raise Exception("transform_type ERROR:{}".format(trans_type))
    return transform
