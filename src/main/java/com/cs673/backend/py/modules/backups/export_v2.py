import argparse
from core.models.plateNet import PlateNetv1
from alphabets import plate_chr
import torch
from basetrainer.utils.converter import pytorch2onnx

if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--weights', type=str, default='saved_model/best.pth',
                        help='weights path')  # from yolov5/models/
    parser.add_argument('--save_path', type=str, default='./best.onnx', help='onnx save path')
    parser.add_argument('--img_size', nargs='+', type=int, default=[48, 168], help='image size')  # height, width
    parser.add_argument('--batch_size', type=int, default=1, help='batch size')
    parser.add_argument('--dynamic', action='store_true', default=False, help='enable dynamic axis in onnx model')
    parser.add_argument('--simplify', action='store_true', default=False, help='simplified onnx')
    parser.add_argument('--trt', action='store_true', default=False, help='support trt')

    opt = parser.parse_args()
    print(opt)
    checkpoint = torch.load(opt.weights)
    cfg = checkpoint['cfg']
    model = PlateNetv1(num_classes=len(plate_chr), cfg=cfg, export=True, trt=opt.trt)
    model.load_state_dict(checkpoint['state_dict'])
    model.eval()

    input = torch.randn(opt.batch_size, 3, 48, 168)
    onnx_file_name = opt.save_path
    pytorch2onnx.convert2onnx(model, input_shape=(opt.batch_size, 3, 48, 168), onnx_file=opt.save_path, simplify=True)
