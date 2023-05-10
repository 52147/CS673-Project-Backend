package com.cs673.backend.serviceImp;

import com.cs673.backend.service.CameraService;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;
import org.springframework.stereotype.Service;

@Service
public class CameraServiceImp implements CameraService {
    private VideoCapture capture;

    public void init() {
        this.capture = new VideoCapture(0);
    }
    public Mat captureFrame() {
        Mat frame = new Mat();
        capture.read(frame);
        return frame;
    }

    public void release() {
        capture.release();
    }
}
