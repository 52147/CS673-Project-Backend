#!/usr/bin/env bash

# 测试CRNN模型
image_dir='data/test_image'
model_file='data/weight/CRNN_Perspective_20230113174750/model/best_model_146_0.9343.pth'
net_type="CRNN"
python demo.py --image_dir $image_dir --model_file $model_file --net_type $net_type --use_detector

# 测试LPRNet模型
image_dir='data/test_image'
model_file='data/weight/LPRNet_Perspective_20230104142632/model/best_model_011_0.9393.pth'
net_type="LPRNet"
python demo.py --image_dir $image_dir --model_file $model_file --net_type $net_type --use_detector

# 测试PlateNet模型
image_dir='data/test_image'
model_file='data/weight/PlateNet_Perspective_20230104102743/model/best_model_186_0.9583.pth'
net_type="PlateNet"
python demo.py --image_dir $image_dir --model_file $model_file --net_type $net_type --use_detector


# 测试视频文件
video_file="data/test-video.mp4" # path/to/video.mp4 测试视频文件，如*.mp4,*.avi等
model_file='data/weight/PlateNet_Perspective_20230104102743/model/best_model_186_0.9583.pth'
net_type="PlateNet"
python demo.py --video_file $video_file --model_file $model_file --net_type $net_type --use_detector


# 测试摄像头
video_file=0 # 测试摄像头ID
model_file='data/weight/PlateNet_Perspective_20230104102743/model/best_model_186_0.9583.pth'
net_type="PlateNet"
python demo.py --video_file $video_file --model_file $model_file --net_type $net_type --use_detector
