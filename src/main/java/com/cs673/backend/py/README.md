# CS673CRNN-Plate-Recognition


1.Test Camera

python demo.py --video_file 0 --model_file data/weight/PlateNet_Perspective_20230104102743/model/best_model_186_0.9583.pth --net_type PlateNet --use_detector

2.Test CRNN Model

python demo.py --image_dir data/test_image --model_file data/weight/CRNN_Perspective_20230113174750/model/best_model_146_0.9343.pth --net_type CRNN --use_detector

3.Test LPRNet Model

python demo.py --image_dir data/test_image --model_file data/weight/LPRNet_Perspective_20230104142632/model/best_model_011_0.9393.pth --net_type LPRNet --use_detector

4.Test PlateNet Model

python demo.py --image_dir data/test_image --model_file data/weight/PlateNet_Perspective_20230104102743/model/best_model_186_0.9583.pth --net_type PlateNet --use_detector
