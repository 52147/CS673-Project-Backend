package com.cs673.backend.controller;

import com.cs673.backend.DTO.FormData;

import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.repository.ParkInfoRepo;
import com.cs673.backend.service.ParkForAllService;

import com.cs673.backend.service.ParkInfoService;
import com.cs673.backend.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@Controller
public class CheckController {
    @Autowired
    private ParkInfoService parkinfoservice;
    @Autowired
    private ParkForAllService parkForAllService;
    
    @PostMapping
    @RequestMapping("/index/check/checkIn")
    @CrossOrigin(origins = "http://localhost:3000")
    public Msg checkIn(@RequestBody ParkInfo data) {
        System.out.println(data);
        parkinfoservice.saveParkInfo(data);
        return Msg.success();
    }
    
    @ResponseBody
    @RequestMapping( "/index/check/checkIn/checkHistory")
    public List<ParkForAll> showHistory(){
        List<ParkForAll> parkingHistoryPage = parkForAllService.findAllParkInForAll();
        return parkingHistoryPage;
    }


    @ResponseBody
    @RequestMapping("/index/check/checkout")
    public Msg CheckOut(FormData data){
        ParkInfo parkInfo= parkinfoservice.findFirstByPlateOrderByEntrance(data.getPlate());
        return Msg.success().add("parkInfo",parkInfo);
    }

    @ResponseBody
    @GetMapping
    @RequestMapping("/index/check/checkPlate")
    public Msg CheckPlate(@RequestBody ParkInfo data){
        ParkInfo parkInfo = parkinfoservice.findFirstByPlateOrderByEntrance(data.getPlate());
        return Msg.success().add("parkinfo", parkInfo);
    }

    @ResponseBody
    @GetMapping
    @RequestMapping("/index/check/checkNum")
    public Msg CheckNum(@RequestBody ParkInfo data){
        ParkInfo parkInfo = parkinfoservice.findParkInfoByParkNum(data.getParkNum());
        return Msg.success().add("parkinfomation", parkInfo);
    }




}
