package com.cs673.backend.controller;

import com.cs673.backend.entity.Garage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>();

    @GetMapping("/camera-stream")
    public SseEmitter handleCameraStream() {
        SseEmitter emitter = new SseEmitter();
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        return emitter;
    }

    @MessageMapping("/camera-frames")
    public void handleCameraFrame(byte[] frameData) {
        // Process the camera frame using OpenCV
        Mat frame = Imgcodecs.imdecode(new MatOfByte(frameData), Imgcodecs.IMREAD_UNCHANGED);
        Mat processedFrame = performImageProcessing(frame);

        // Send the processed frame to all connected clients using SSE
        byte[] processedFrameData = toImageData(processedFrame);
        for (SseEmitter emitter : emitters) {
            try {
                emitter.send(SseEmitter.event().data(processedFrameData));
            } catch (IOException e) {
                emitter.completeWithError(e);
                emitters.remove(emitter);
            }
        }
    }

    private Mat performImageProcessing(Mat frame) {
        // TODO: Add your image processing logic here
        return frame;
    }

    private byte[] toImageData(Mat frame) {
        MatOfByte buffer = new MatOfByte();
        Imgcodecs.imencode(".jpg", frame, buffer);
        return buffer.toArray();
    }


}
