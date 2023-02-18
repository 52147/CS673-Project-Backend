package com.cs673.backend.controller;

import com.cs673.backend.DTO.FormData;
import com.cs673.backend.service.ParkInfoService;
import com.cs673.backend.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@RestController
@RequestMapping("/index/check/checkIn")
public class CheckController {
    @Autowired
    private ParkInfoService parkinfoservice;
    @PostMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public Msg checkIn(@RequestBody FormData data) {
        System.out.println(data);

        return Msg.success();
    }
}
