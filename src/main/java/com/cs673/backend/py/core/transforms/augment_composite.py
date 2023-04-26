# -*-coding: utf-8 -*-
"""
    @Author : panjq
    @E-mail : pan_jinquan@163.com
    @Date   : 2021-09-03 15:30:50
"""
import sys
import os

sys.path.insert(0, os.getcwd())
import cv2
import numpy as np
import random
import math
from tqdm import tqdm
from pybaseutils import file_utils, image_utils


class RandomImageComposite(object):
    """
    Random Alpha Blending
    随机图像融合，需要提供RGB+Alpha(matte)
    合成: https://github.com/wuhuikai/GP-GAN
    图像融合的算法：https://nbviewer.org/github/yourwanghao/CMUComputationalPhotography/blob/master/class11/Notebook11.ipynb
    """

    def __init__(self, p=1.0, bg_dir="bg_image/", is_rgb=True, mask_type="alpha"):
        """
        :param p: 概率
        :param bg_dir: 背景图库，PS：背景图不能含有目标的对象，避免背景的干扰
        :param is_rgb: 输入的图片是否是RGB图片
        :param mask_type: 输入的mask的类型：
                            alpha: 输入mask是alpha图，则可以直接融合了
                            VOC：输入mask是VOC的Mask，则需要get_voc_alpha()进行处理，获得alpha图
        """
        self.p = p
        self.bg_image_list = file_utils.get_files_lists(bg_dir, subname="")
        self.bg_nums = len(self.bg_image_list)
        self.is_rgb = is_rgb
        if mask_type.lower() == "VOC".lower():
            mask_transform = self.get_voc_alpha
        elif mask_type.lower() == "alpha".lower():
            mask_transform = None
        else:
            raise Exception("Error:{}".format(mask_type))
        self.mask_transform = mask_transform
        print("have bg_image_list :{}".format(len(self.bg_image_list)))

    def random_read_bg_image(self, is_rgb=False, crop_rate=0.6):
        index = int(np.random.uniform(0, self.bg_nums))
        image_path = self.bg_image_list[index]
        # image_path = self.bg_image_list[0]
        image = cv2.imread(image_path)
        if is_rgb:
            image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        h_img, w_img, _ = image.shape
        xmin, ymin, xmax, ymax = self.extend_bboxes([0, 0, w_img, h_img], [0.8, 0.8])
        crop_xmin = int(random.uniform(0, xmin * crop_rate))
        crop_ymin = int(random.uniform(0, ymin * crop_rate))
        crop_xmax = int(min(w_img, random.uniform(w_img - xmax * crop_rate, w_img)))
        crop_ymax = int(min(h_img, random.uniform(h_img - ymax * crop_rate, h_img)))
        if random.random() < 0.5:
            image = image[:, ::-1, :]
        image = image[crop_ymin: crop_ymax, crop_xmin: crop_xmax]
        return image

    @staticmethod
    def extend_bboxes(box, scale=[1.0, 1.0]):
        """
        :param box: [xmin, ymin, xmax, ymax]
        :param scale: [sx,sy]==>(W,H)
        :return:
        """
        sx = scale[0]
        sy = scale[1]
        xmin, ymin, xmax, ymax = box[:4]
        cx = (xmin + xmax) / 2
        cy = (ymin + ymax) / 2

        ex_w = (xmax - xmin) * sx
        ex_h = (ymax - ymin) * sy
        ex_xmin = cx - 0.5 * ex_w
        ex_ymin = cy - 0.5 * ex_h
        ex_xmax = ex_xmin + ex_w
        ex_ymax = ex_ymin + ex_h
        ex_box = [ex_xmin, ex_ymin, ex_xmax, ex_ymax]
        return ex_box

    @staticmethod
    def image_composite(image: np.ndarray, alpha: np.ndarray, bg_img=(219, 142, 67)):
        """
        图像融合：合成图 = 前景*alpha+背景*(1-alpha)
        https://blog.csdn.net/guduruyu/article/details/71439733
        更有效的C++实现: https://www.aiuai.cn/aifarm1237.html
        :param image: RGB图像(uint8)
        :param alpha: 单通道的alpha图像(uint8)
        :param bg_img: 背景图像,可以是任意的分辨率图像，也可以指定指定纯色的背景
        :return: 返回与背景合成的图像
        """
        if isinstance(bg_img, tuple) or isinstance(bg_img, list):
            bg = np.zeros_like(image, dtype=np.uint8)
            bg_img = np.asarray(bg[:, :, 0:3] + bg_img, dtype=np.uint8)
        if len(alpha.shape) == 2:
            # alpha = cv2.cvtColor(alpha, cv2.COLOR_GRAY2BGR)
            alpha = alpha[:, :, np.newaxis]
        if alpha.dtype == np.uint8:
            alpha = np.asarray(alpha / 255.0, dtype=np.float32)
        sh, sw, d = image.shape
        bh, bw, d = bg_img.shape
        ratio = [sw / bw, sh / bh]
        ratio = max(ratio)
        if ratio > 1:
            bg_img = cv2.resize(bg_img, dsize=(math.ceil(bw * ratio), math.ceil(bh * ratio)))
        bg_img = bg_img[0: sh, 0: sw]
        image = image * alpha + bg_img * (1 - alpha)
        image = np.asarray(np.clip(image, 0, 255), dtype=np.uint8)
        return image

    @staticmethod
    def get_voc_alpha(mask, bg_index=0):
        """获得VOC的Alpha图"""
        matte = np.array(mask)
        matte[matte == 255] = 0
        matte[matte > bg_index] = 255
        # OpenCV定义的结构矩形元素
        kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (7, 7))
        matte = cv2.dilate(matte, kernel)  # 膨胀图像（去除过度白边）
        matte = cv2.erode(matte, kernel)  # 腐蚀图像
        matte = cv2.blur(matte, ksize=(5, 5))
        return matte

    def __call__(self, image, mask):
        """
        Args:
            image (numpy Image): Image to be cropped.
        Returns:
            PIL Image: Cropped image.
        """
        if random.random() < self.p:
            bg_image = self.random_read_bg_image(is_rgb=self.is_rgb)
            # alpha = self.get_voc_alpha(mask)
            if self.mask_transform:
                alpha = self.mask_transform(mask)
            else:
                alpha = mask.copy()
            image = self.image_composite(image, alpha, bg_img=bg_image)
        return image


def read_image(image_file: str, use_rgb=True):
    # image = cv2.imread(image_file)
    image = cv2.imread(image_file, cv2.IMREAD_UNCHANGED)
    # image = cv2.imread(image_file, cv2.IMREAD_IGNORE_ORIENTATION | cv2.IMREAD_UNCHANGED)
    if use_rgb:
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    return image


def load_annotations(image_file: str, ignore_label=255):
    """
    不同数据集的Mask读取方式不一样：
    (1)matting_human_datasets: https://github.com/aisegmentcn/matting_human_datasets
        image = cv2.imread('png图像文件路径', cv2.IMREAD_UNCHANGED)
        alpha = image[:,:,3] # 取第4通道即可
    (2) Adobe_Deep_Matting_Dataset:
        alpha = cv2.cvtColor(image, cv2.COLOR_BGRA2GRAY)
    :param image_file:
    :param ignore_label:
    :return: alpha
    """
    dataset = "Adobe"
    if dataset.lower() == "Adobe".lower():
        alpha = cv2.imread(image_file, cv2.IMREAD_GRAYSCALE)
        # alpha = cv2.cvtColor(image, cv2.COLOR_BGRA2GRAY)
    else:
        image = cv2.imread(image_file, cv2.IMREAD_UNCHANGED)
        alpha = image[:, :, 3]
    # image_utils.cv_show_image("image", alpha)
    return alpha


def create_composite_images(fg_dir, bg_dir, mask_dir, out_dir=None, vis=True):
    """
    :param fg_dir: 前景图片，或者原始图片
    :param bg_dir: 背景图片
    :param mask_dir: mask图片
    :param out_dir: 保存融合图片的目录
    :return:
    """
    use_rgb = True
    if out_dir:
        file_utils.create_dir(out_dir)
    image_list = file_utils.get_files_lists(fg_dir)
    image_list = file_utils.get_sub_list(image_list, fg_dir)
    composite = RandomImageComposite(bg_dir=bg_dir, is_rgb=use_rgb)
    for name in tqdm(image_list):
        image_id = name.split(".")[0]
        image_path = os.path.join(fg_dir, name)
        if os.path.exists(image_path):
            image = read_image(image_path, use_rgb=use_rgb)
            mask = image.copy()
            image = 255 - image
            dst = composite(image, mask)
            if out_dir:
                out_path = os.path.join(out_dir, image_id + ".png")
                cv2.imwrite(out_path, cv2.cvtColor(dst, cv2.COLOR_RGB2BGR))
            if vis:
                vis = image_utils.image_hstack([image, mask, dst])
                image_utils.cv_show_image("RGBA", vis)


def create_dataset(fg_dir, bg_dir, mask_dir, out_dir, nums=50):
    for i in range(0, nums):
        out_path = os.path.join(out_dir, "composite{:0=2d}".format(i))
        print("save composite images in:", out_path)
        create_composite_images(fg_dir, bg_dir, mask_dir, out_path, vis=True)


if __name__ == "__main__":
    bg_dir = "/home/dm/nasdata/dataset/segmentation/BACKGROUND/train/DM"
    fg_dir = '/home/dm/nasdata/dataset-dmai/bihua/30种笔画/1'
    mask_dir = None
    out_dir = '/home/dm/nasdata/dataset-dmai/bihua/composite'
    create_dataset(fg_dir, bg_dir, mask_dir, out_dir)
