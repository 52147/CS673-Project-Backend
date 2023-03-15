package com.cs673.backend.controller;

import com.cs673.backend.entity.Garage;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.service.GarageService;
import com.cs673.backend.service.ParkInfoService;
import com.cs673.backend.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class GarageController {
    @Autowired
    private GarageService garageservice;

    @RequestMapping("/index/garage")
    public Garage show() {
        return garageservice.findGarageData();
    }
}
