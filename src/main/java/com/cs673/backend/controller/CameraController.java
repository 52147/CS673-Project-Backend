package com.cs673.backend.controller;

import com.cs673.backend.entity.Garage;
import com.cs673.backend.service.CameraService;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CameraController {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();
    private final CameraService cameraService;

    @Autowired
    public CameraController(CameraService cameraService) {
        this.cameraService = cameraService;
    }

    @GetMapping("/camera-stream")
    public SseEmitter handleCameraStream() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));

        new Thread(() -> {
            while (true) {
                try {
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

    private byte[] toImageData(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return buffer.toArray();
    }
}

