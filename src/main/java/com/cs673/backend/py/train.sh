#!/usr/bin/env bash
# 训练PlateNet模型
python train.py -c  configs/config_platenet.yaml

# 训练CRNN模型
python train.py -c  configs/config_crnn.yaml

# 训练LPRNet模型
python train.py -c  configs/config_lprnet.yaml