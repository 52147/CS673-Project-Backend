package com.cs673.backend.controller;

import com.cs673.backend.entity.Garage;
import com.cs673.backend.service.CameraService;
import com.cs673.backend.serviceImp.YOLOService;
import jep.JepException;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.pytorch.Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CameraController {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final CameraService cameraService;

    private final YOLOService yoloService;

    @Autowired
    public CameraController(CameraService cameraService, YOLOService yoloService) {
        this.cameraService = cameraService;
        this.yoloService = yoloService;
    }

    @GetMapping("/camera-stream")
    public SseEmitter handleCameraStream() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));

        new Thread(() -> {
            while (true) {
                try {
                    cameraService.init();
                    Mat frame = cameraService.captureFrame();
                    byte[] imageData = toImageData(frame);
                    emitter.send(SseEmitter.event().data(imageData));
                } catch (IOException e) {
                    emitter.completeWithError(e);
                }
            }
        }).start();

        return emitter;
    }


    @GetMapping("/camera-test")
    public void CameraTest() throws JepException {
        Mat frame = cameraService.captureFrame();
        Imgcodecs.imwrite("camera.jpg", frame);
        System.out.println("Frame captured!");
    }

    private byte[] toImageData(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return buffer.toArray();
    }
}

