package com.cs673.backend.controller;

import com.cs673.backend.entity.Garage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ImageController {
    @RequestMapping("/rec")
    public String rec(@RequestParam("image") MultipartFile imageFile) {

    }
}
