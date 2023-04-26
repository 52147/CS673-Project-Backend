package com.cs673.backend.serviceImp;

import com.cs673.backend.service.CameraService;
import jep.JepException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class CameraServiceImp implements CameraService {
    private VideoCapture capture;
    private ScheduledExecutorService scheduledExecutorService;

    @Autowired
    private YOLOService yoloService;

    @PostConstruct
    public void init() {
        capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.err.println("Failed to open the camera.");
        }

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduleCaptureImage(0);
    }

    public void scheduleCaptureImage(long delayInSeconds) {
        scheduledExecutorService.schedule(this::captureImage, delayInSeconds, TimeUnit.SECONDS);
    }

    public Mat captureFrame() {
        Mat frame = new Mat();
        if (capture.read(frame)) {
            Imgcodecs.imwrite("src/main/java/com/cs673/backend/image/camera.jpg", frame);
            System.out.println("Image captured!");
        } else {
            System.out.println("Failed to capture image.");
        }
        return frame;
    }

    public void captureImage() {
        try {
            long delay = 1;
            captureFrame();
            String plate = yoloService.detectObjects("src/main/java/com/cs673/backend/image");
            System.out.println(plate);
            if(plate.length() > 2) {
                delay = 1;
            }
            scheduleCaptureImage(delay);
        } catch (JepException e) {
            System.err.println("Error in captureImage: " + e.getMessage());
        }
    }

    @PreDestroy
    public void release() {
        capture.release();

        // Shutdown the ScheduledExecutorService
        scheduledExecutorService.shutdown();
        try {
            if (!scheduledExecutorService.awaitTermination(5, TimeUnit.SECONDS)) {
                scheduledExecutorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            scheduledExecutorService.shutdownNow();
        }
    }
}
