package com.cs673.backend.controller;

import com.cs673.backend.DTO.FormData;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.service.ParkInfoService;
import com.cs673.backend.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Controller
public class CheckController {
    @Autowired
    private ParkInfoService parkinfoservice;
    @PostMapping
    @RequestMapping("/index/checkIn")
    @CrossOrigin(origins = "http://localhost:3000")
    public Msg checkIn(@RequestBody ParkInfo data) {
        System.out.println(data);
        /*parkinfoservice.saveParkInfo(data);*/
        return Msg.success();
    }
}
