# -*-coding: utf-8 -*-
"""
    @Author : PKing
    @E-mail : 390737991@qq.com
    @Date   : 2022-12-30 10:40:48
    @Brief  :
"""
import os
import sys
import cv2
import torch
import numpy as np
import argparse
from typing import List, Tuple
from core.models.build_model import get_models
from core.transforms import build_transform
from core.utils import image_correction
from modules.yolov5.engine.yolov5_detector import Yolov5Detector
from pybaseutils import file_utils, image_utils
from basetrainer.utils.converter import pytorch2onnx

script_path = os.path.abspath(sys.argv[0])
ROOT = os.path.dirname(script_path)
PLATE_TABLE = "#京沪津渝冀晋蒙辽吉黑苏浙皖闽赣鲁豫鄂湘粤桂琼川贵云藏陕甘青宁新学警港澳挂使领民航危0123456789ABCDEFGHJKLMNPQRSTUVWXYZ险品"


class Recognizer(object):
    def __init__(self, model_file, net_type, class_name=None, use_detector=True, input_size=(),
                 rgb_mean=[0.5, 0.5, 0.5], rgb_std=[0.5, 0.5, 0.5], alignment=True, export=True, device="cuda:0"):
        """
        :param model_file: 车牌识别模型文件
        :param net_type: 车牌识别模型名称
        :param class_name: 车牌所有字符类别
        :param use_detector: 是否检测车牌
        :param input_size: 模型输入大小
        :param rgb_mean: rgb_mean
        :param rgb_std: rgb_std
        :param alignment: 是否进行车牌矫正
        :param export: 是否导出ONNX模型
        :param device: 运行设备
        """
        self.device = device
        self.rgb_mean = rgb_mean
        self.rgb_std = rgb_std
        self.net_type = net_type
        self.alignment = alignment  # 是否进行车牌倾斜矫正
        if not input_size:
            input_size = (168, 48) if net_type.lower() == "PlateNet".lower() else input_size
            input_size = (94, 24) if net_type.lower() == "LPRNet".lower() else input_size
            input_size = (160, 32) if net_type.lower() == "CRNN".lower() else input_size
        self.input_size = input_size
        self.use_detector = use_detector
        self.transform = build_transform.image_transform(input_size=self.input_size,
                                                         rgb_mean=self.rgb_mean,
                                                         rgb_std=self.rgb_std,
                                                         trans_type="test")

        # 类别
        if not class_name: class_name = [n for n in PLATE_TABLE]
        self.class_name, self.class_dict = file_utils.parser_classes(class_name)
        self.num_classes = len(self.class_name)
        # 加载车牌识别模型
        self.model = self.build_model(model_file)
        # 加载车牌检测模型
        if self.use_detector:
            # imgsz, weights = 640, os.path.join(ROOT, "modules/yolov5/data/model/yolov5s_640/weights/best.pt")
            imgsz, weights = 320, "src/main/java/com/cs673/backend/py/modules/yolov5/data/model/yolov5s05_320/weights/best.pt"
            self.detector = Yolov5Detector(weights, imgsz=imgsz, conf_thres=0.5, iou_thres=0.5, device=self.device)
        # 转换为ONNX模型
        if export:
            onnx_file = model_file[:-len("pth")] + "onnx"
            pytorch2onnx.convert2onnx(self.model, input_shape=(1, 3, input_size[1], input_size[0]),
                                      onnx_file=onnx_file, simplify=True)
            print("convert model to onnx:{}".format(onnx_file))

    def build_model(self, model_file):
        """
        构建模型
        :param model_file:
        :return:
        """
        check_point = torch.load(model_file, map_location="cpu")
        model_state = check_point['state_dict']
        model = get_models(net_type=self.net_type, num_classes=self.num_classes,
                           input_size=self.input_size, is_train=False)  # export  True 用来推理
        # model =build_lprnet(num_classes=len(plate_chr),export=True)
        model.load_state_dict(model_state)
        #model.to(self.device)
        model.eval()
        return model

    def plates_detect(self, image):
        """
        车牌检测
        :param image: BGR Image
        :return:
        """
        dets = self.detector.detect(image=image, vis=False)
        dets = dets[0] if len(dets) > 0 else []
        return dets

    def plates_correction(self, images: List[np.ndarray]):
        """
        车牌倾斜矫正
        :param images:
        :return:
        """
        cimages = []
        for img in images:
            img, angle = image_correction.ImageCorrection.correct(img, vis=False)
            cimages.append(img)
        return cimages

    def plates_recognize(self, image):
        """
        车牌识别
        :param image:
        :return:
        """
        h, w = image.shape[:2]
        dets = np.asarray([[0, 0, w, h, -1, -1]])
        result = {"dets": [], "plates": []}
        if self.use_detector:
            dets = self.plates_detect(image)
            image = image_utils.get_bboxes_crop(image, dets)
            if len(image) == 0: return result
        with torch.no_grad():
            input_tensor = self.preprocess(image, alignment=self.alignment)  # torch.Size([1, 3, 48, 168])
            outputs = self.model(input_tensor)  # classifier prediction
            outputs = outputs.argmax(dim=2)  # torch.Size([1, 21, 78])
            outputs = outputs.cpu().detach().numpy()
        preb_labels = self.postprocess(outputs)
        plates = self.map_class_name(preb_labels)
        result = {"dets": dets, "plates": plates}
        return result

    def preprocess(self, images, alignment=True):
        """
        数据预处理
        :param images: BGR images
        :param alignment: 车牌倾斜矫正
        :return:
        """
        if not isinstance(images, list): images = [images]
        if alignment: images = self.plates_correction(images)
        image_tensors = []
        for img in images:
            if len(img.shape) == 2: img = cv2.cvtColor(img, cv2.COLOR_GRAY2BGR)
            image_tensor = self.transform(img)
            image_tensors.append(torch.unsqueeze(image_tensor, dim=0))
        image_tensors = torch.cat(image_tensors)
        return image_tensors

    def postprocess(self, outputs):
        """
        车牌识别后处理
        :param outputs:
        :return:
        """
        pred_labels = []
        for output in outputs:
            label = 0
            pred_label = []
            for i in range(len(output)):
                if output[i] != 0 and output[i] != label:
                    pred_label.append(output[i])
                label = output[i]
            pred_labels.append(pred_label)
        return pred_labels

    def map_class_name(self, pred_labels):
        """
        :param pred_labels:
        :return:
        """
        pred_names = []
        for preb_label in pred_labels:
            preb_label = [self.class_name[int(l)] for l in preb_label]
            pred_names.append("".join(preb_label))
        return pred_names

    def draw_result(self, image, result: dict, thickness=4, fontScale=0.6):
        """绘制车牌识别结果"""
        dets = result["dets"]
        if len(dets) > 0:
            boxes = dets[:, 0:4]
            score = dets[:, 4]
            plates = result["plates"]
            boxes_name = ["{}   {:3.3f}".format(n, s) for n, s in zip(plates, score)]
            image = image_utils.draw_image_bboxes_text(image, boxes=boxes, boxes_name=boxes_name, drawType="chinese",
                                                       color=(0, 0, 255), thickness=thickness, fontScale=fontScale)
        return image

    def start_capture(self, video_file, save_video=None, detect_freq=1, vis=True):
        """
        start capture video
        :param video_file: *.avi,*.mp4,...
        :param save_video: *.avi
        :param detect_freq:
        :return:
        """
        video_cap = image_utils.get_video_capture(video_file)
        width, height, numFrames, fps = image_utils.get_video_info(video_cap)
        if save_video:
            self.video_writer = image_utils.get_video_writer(save_video, width, height, fps)
        count = 0
        while True:
            if count % detect_freq == 0:
                # 设置抽帧的位置
                if isinstance(video_file, str): video_cap.set(cv2.CAP_PROP_POS_FRAMES, count)
                isSuccess, frame = video_cap.read()
                if not isSuccess:
                    break
                result = self.plates_recognize(frame)
                frame = self.draw_result(frame, result, thickness=5, fontScale=0.8)
                if vis:
                    image_utils.cv_show_image("image", frame, use_rgb=False, delay=30)
            if save_video:
                self.video_writer.write(frame)
            count += 1
        video_cap.release()

    def detect_image_dir(self, image_dir, out_dir=None, vis=True):
        """
        :param image_dir: 车牌图片目录
        :param out_dir:
        :param vis:
        :return:
        """
        image_list = file_utils.get_files_lists(image_dir)
        for image_file in image_list:
            image = image_utils.read_image_ch(image_file, use_rgb=False)  # BGR image
            result = self.plates_recognize(image)
            if(result["plates"]):
                if result["dets"][:,4] > 0.8:
                    return result["plates"][0]
                else:
                    return ""
            else:
                return ""
            image = self.draw_result(image, result)
            if vis:
                image_utils.cv_show_image("image", image, use_rgb=False)
            if out_dir:
                out_file = file_utils.create_dir(out_dir, None, os.path.basename(image_file))
                cv2.imwrite(out_file, image)


def get_argparse():
    image_dir = 'data/test_image'  # 测试图片
    video_file = "data/test-video2.mp4"  # path/to/video.mp4 测试视频文件，如*.mp4,*.avi等
    # model_file = 'data/weight/PlateNet_Perspective_20230104102743/model/best_model_186_0.9583.pth'
    # net_type = "PlateNet"
    model_file = 'data/weight/CRNN_Perspective_20230113174750/model/best_model_146_0.9343.pth'
    net_type = "CRNN"
    out_dir = "output/test-result"  # 保存检测结果
    parser = argparse.ArgumentParser()
    parser.add_argument('--model_file', type=str, default=model_file, help='path/to/model.pth')
    parser.add_argument('--net_type', type=str, default=net_type, help='set model type')
    parser.add_argument('--video_file', type=str, default=None, help='camera id or video file')
    parser.add_argument('--image_dir', type=str, default=image_dir, help='path/to/image-dir')
    parser.add_argument('--out_dir', type=str, default=out_dir, help='save result image directory')
    parser.add_argument('--use_detector', action='store_true', help='whether to detect plate', default=True)
    parser.add_argument('--export', action='store_true', help='whether to export ONNX', default=True)
    cfg = parser.parse_args()
    return cfg

def main(image_dir):
    model_file = "src/main/java/com/cs673/backend/py/data/weight/CRNN_Perspective_20230113174750/model/best_model_146_0.9343.pth"
    d = Recognizer(model_file=model_file, net_type="CRNN", use_detector=True, export=True)
    return d.detect_image_dir(image_dir, out_dir="output/test-result", vis=True)

'''
if __name__ == '__main__':
    opt = get_argparse()
    d = Recognizer(model_file=opt.model_file, net_type=opt.net_type, use_detector=opt.use_detector, export=opt.export)
    if isinstance(opt.video_file, str):
        if len(opt.video_file) == 1: opt.video_file = int(opt.video_file)
        save_video = os.path.join(opt.out_dir, "result.avi") if opt.out_dir else None
        d.start_capture(opt.video_file, save_video, detect_freq=1, vis=True)
    else:
        d.detect_image_dir(opt.image_dir, out_dir=opt.out_dir, vis=True)
'''