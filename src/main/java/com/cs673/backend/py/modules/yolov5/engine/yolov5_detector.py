"""Run inference with a YOLOv5 model on images, videos, directories, streams

Usage:
    $ python path/to/detect.py --source path/to/img.jpg --weights yolov5s.pt --img 640
"""
import sys
import os

sys.path.insert(0, os.path.dirname(os.path.dirname(__file__)))
import cv2
import argparse
import numpy as np
from typing import List
from utils.datasets import LoadImages
from pybaseutils import file_utils, image_utils
from engine.inference import yolov5


class Yolov5Detector(yolov5.YOLOv5):
    def __init__(self,
                 weights='yolov5s.pt',  # model.pt path(s)
                 imgsz=640,  # inference size (pixels)
                 conf_thres=0.5,  # confidence threshold
                 iou_thres=0.5,  # NMS IOU threshold
                 max_det=1000,  # maximum detections per image
                 class_name=None,  # filter by class: --class 0, or --class 0 2 3
                 classes=None,  # filter by class: --class 0, or --class 0 2 3
                 agnostic_nms=False,  # class-agnostic NMS
                 augment=False,  # augmented inference
                 half=False,  # use FP16 half-precision inference
                 visualize=False,  # visualize features
                 batch_size=1,
                 device='cuda:0',  # cuda device, i.e. 0 or 0,1,2,3 or cpu
                 fix_inputs=False,
                 ):
        super(Yolov5Detector, self).__init__(weights=weights,  # model.pt path(s)
                                             imgsz=imgsz,  # inference size (pixels)
                                             conf_thres=conf_thres,  # confidence threshold
                                             iou_thres=iou_thres,  # NMS IOU threshold
                                             max_det=max_det,  # maximum detections per image
                                             class_name=class_name,  # filter by class: --class 0, or --class 0 2 3
                                             classes=classes,  # filter by class: --class 0, or --class 0 2 3
                                             agnostic_nms=agnostic_nms,  # class-agnostic NMS
                                             augment=augment,  # augmented inference
                                             half=half,  # use FP16 half-precision inference
                                             visualize=visualize,  # visualize features
                                             batch_size=batch_size,
                                             device=device,  # cuda device, i.e. 0 or 0,1,2,3 or cpu
                                             fix_inputs=fix_inputs, )

    def detect(self, image: List[np.ndarray] or np.ndarray, vis: bool = False) -> List[List]:
        """
        :param image: 图像或者图像列表,BGR格式
        :param vis: 是否可视化显示检测结果
        :return: 返回检测结果[[List]], each bounding box is in [x1,y1,x2,y2,conf,cls] format.
        """
        if isinstance(image, np.ndarray): image = [image]
        dets = super().inference(image)
        if vis:
            self.draw_result(image, dets)
        return dets

    def detect_image_loader(self, image_dir, vis=True):
        # Dataloader
        dataset = LoadImages(image_dir, img_size=self.imgsz, stride=self.stride)
        # Run inference
        for path, input_image, image, vid_cap in dataset:
            # image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
            dets = self.detect(image, vis=vis)

    def detect_image_dir(self, image_dir, out_dir=None, vis=True):
        # Dataloader
        dataset = file_utils.get_files_lists(image_dir)
        # Run inference
        for path in dataset:
            image = cv2.imread(path)  # BGR
            # rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
            dets = self.detect(image, vis=False)
            if vis:
                image = self.draw_result([image], dets)[0]
            if out_dir:
                out_file = file_utils.create_dir(out_dir, None, os.path.basename(path))
                cv2.imwrite(out_file, image)

    def draw_result(self, image: List[np.ndarray] or np.ndarray, dets, delay=0):
        """
        :param image: 图像或者图像列表
        :param dets: 是否可视化显示检测结果
        """
        vis_image = []
        for i in range(len(dets)):
            image = self.draw_image(image[i], dets[i], delay=delay)
            vis_image.append(image)
        return vis_image

    def draw_image(self, image, dets, delay=0):
        # image = cv2.cvtColor(image, cv2.COLOR_RGB2BGR)
        # (xmin,ymin,xmax,ymax,conf, cls)
        boxes = dets[:, 0:4]
        conf = dets[:, 4:5]
        cls = dets[:, 5]
        labels = [int(c) for c in cls]
        image_utils.draw_image_detection_bboxes(image, boxes, conf, labels, class_name=self.names,thickness=4)
        # for *box, conf, cls in reversed(dets):
        #     c = int(cls)  # integer class
        #     label = "{}{:.2f}".format(self.names[c], conf)
        #     plot_one_box(box, image, label=label, color=colors(c, True), line_thickness=2)
        image_utils.cv_show_image("image", image, use_rgb=False, delay=delay)
        return image


def parse_opt():
    # weights = 'pretrained/face_person.pt'
    # weights = 'pretrained/yolov5s.pt'
    # image_dir = '/home/dm/nasdata/dataset-dmai/MCLZ/JPEGImages'
    # weights = "../runs/mclz/exp2/weights/best.pt"

    # image_dir = '/home/dm/nasdata/dataset-dmai/handwriting/word-det/word-old/JPEGImages'
    image_dir = '/home/dm/nasdata/dataset-dmai/handwriting/word-det/word-lesson1-16/JPEGImages'
    image_dir = '/home/dm/nasdata/dataset-dmai/handwriting/word-det/competition/test1/小中组crop'
    # image_dir = '/home/dm/nasdata/dataset-dmai/handwriting/word-det/competition/zip/Scan Data from FX-1C7D22324CF9'
    # image_dir = '/home/dm/nasdata/dataset-dmai/handwriting/word-det/competition/test'
    # weights = "/home/dm/nasdata/Detector/YOLO/yolov5/runs/word/exp6/weights/best.pt"
    # weights = "/home/dm/nasdata/dataset-dmai/handwriting/word-det/model/yolov5s-anchor-mosaic0.0/weights/best.pt"
    weights = "/home/dm/nasdata/dataset-dmai/handwriting/word-det/model/yolov5s-baseline/weights/best.pt"
    # weights = "/home/dm/nasdata/Detector/YOLO/yolov5/runs/word/exp2/weights/best.pt"
    out_dir = image_dir + "_result"
    class_name = ["word"]
    parser = argparse.ArgumentParser()
    parser.add_argument('--weights', nargs='+', type=str, default=weights, help='model.pt')
    parser.add_argument('--image_dir', type=str, default=image_dir, help='file/dir/URL/glob, 0 for webcam')
    parser.add_argument('--out_dir', type=str, default=out_dir, help='save det result image')
    parser.add_argument('--imgsz', '--img', '--img-size', type=int, default=640, help='inference size (pixels)')
    parser.add_argument('--conf-thres', type=float, default=0.3, help='confidence threshold')
    parser.add_argument('--iou-thres', type=float, default=0.2, help='NMS IoU threshold')
    parser.add_argument('--max-det', type=int, default=1000, help='maximum detections per image')
    parser.add_argument('--device', default='cuda:0', help='cuda device, i.e. 0 or 0,1,2,3 or cpu')
    parser.add_argument('--classes', nargs='+', type=int, help='filter by class: --class 0, or --class 0 2 3')
    parser.add_argument('--class_name', nargs='+', type=list, default=class_name)
    parser.add_argument('--agnostic-nms', action='store_true', help='class-agnostic NMS')
    parser.add_argument('--augment', action='store_true', help='augmented inference')
    opt = parser.parse_args()
    return opt


if __name__ == "__main__":
    opt = parse_opt()
    d = Yolov5Detector(weights=opt.weights,  # model.pt path(s)
                       imgsz=opt.imgsz,  # inference size (pixels)
                       conf_thres=opt.conf_thres,  # confidence threshold
                       iou_thres=opt.iou_thres,  # NMS IOU threshold
                       max_det=opt.max_det,  # maximum detections per image
                       class_name=opt.class_name,  # filter by class: --class 0, or --class 0 2 3
                       classes=opt.classes,  # filter by class: --class 0, or --class 0 2 3
                       agnostic_nms=opt.agnostic_nms,  # class-agnostic NMS
                       augment=opt.augment,  # augmented inference
                       device=opt.device,  # cuda device, i.e. 0 or 0,1,2,3 or cpu
                       )
    # d.detect_image_loader(opt.image_dir)
    d.detect_image_dir(opt.image_dir, opt.out_dir)
