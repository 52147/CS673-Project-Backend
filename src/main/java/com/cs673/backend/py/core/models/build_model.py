# -*-coding: utf-8 -*-
"""
    @Author : PKing
    @E-mail : 390737991@qq.com
    @Date   : 2022-12-30 11:24:48
    @Brief  :
"""
import torch
from core.models.PlateNet import PlateNet
from core.models.LPRNet import LPRNet
from core.models.crnn import CRNN


def get_models(net_type: str, num_classes, input_size, is_train=False):
    """
    :param net_type:
    :param num_classes:
    :param input_size:
    :param is_train:
    :return:
    """
    if net_type.lower() == "PlateNet".lower():
        # cfg =[8,8,16,16,'M',32,32,'M',48,48,'M',64,128] #small model
        # cfg =[16,16,32,32,'M',64,64,'M',96,96,'M',128,256]#medium model
        cfg = [32, 32, 64, 64, 'M1', 128, 128, 'M1', 196, 196, 'M0', 256, 256]
        model = PlateNet(num_classes=num_classes, cfg=cfg, is_train=is_train)
    elif net_type.lower() == "LPRNet".lower():
        model = LPRNet(num_classes=num_classes, dropout_rate=0.5, is_train=is_train)
    elif net_type.lower() == "CRNN".lower():
        model = CRNN(imgH=input_size[1], nc=3, nclass=num_classes, nh=256, is_train=is_train)
    else:
        model = None
    return model


if __name__ == "__main__":
    from basetrainer.utils import torch_tools

    # device = torch.device("cuda" if torch.cuda.is_available() else "cpu")
    device = "cpu"
    batch_size = 1
    num_classes = 78
    net_type = "PlateNet"
    input_size = ()
    input_size = (168, 48) if net_type.lower() == "PlateNet".lower() else input_size
    input_size = (94, 24) if net_type.lower() == "LPRNet".lower() else input_size
    input_size = (160, 32) if net_type.lower() == "CRNN".lower() else input_size
    inputs = torch.randn(size=(batch_size, 3, input_size[1], input_size[0])).to(device)
    model = get_models(net_type, num_classes=num_classes, input_size=input_size).to(device)
    outputs = model(inputs)
    print("inputs.shape :{}".format(inputs.shape))
    print("outputs.shape:{}".format(outputs.shape))
    # file = "_".join([net_type, str(input_size[0]), str(input_size[1]), str(num_classes)])
    # torch.save(model.state_dict(), file + ".pth")
    torch_tools.nni_summary_model(model, batch_size=1, input_size=input_size, device=device)
    # summary(model, input_size=(1, input_size[1], input_size[0]), batch_size=batch_size, device=device)
    # stat(model, (1, input_size[0], input_size[1]))
    # print("===" * 10)
    # torch_tools.plot_model(model, output)
