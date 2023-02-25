package com.cs673.backend.controller;

import com.cs673.backend.DTO.FormData;

import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.service.ParkForAllService;

import com.cs673.backend.service.ParkInfoService;
import com.cs673.backend.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public Msg showHistory(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<ParkForAll> parkingHistoryPage = parkForAllService.findAllParkInForAll(pageable);
        return Msg.success().add("parkInfoAll",parkingHistoryPage);
    }


    @ResponseBody
    @RequestMapping("/index/check/checkout")
    public Msg CheckOut(FormData data){
        ParkInfo parkInfo= parkinfoservice.findFirstByPlateOrderByEntrance(data.getPlate());
        return Msg.success().add("parkInfo",parkInfo);
    }
}
