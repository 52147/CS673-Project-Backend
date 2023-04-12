package com.cs673.backend.service;

import org.opencv.core.Mat;

public interface CameraService {
    public void init();
    public Mat captureFrame();
    public void release();
}
