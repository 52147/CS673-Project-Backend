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

import java.sql.Time;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CheckController {
    @Autowired
    private ParkInfoService parkinfoservice;
    @Autowired
    private ParkForAllService parkForAllService;
    
    @PostMapping
    @RequestMapping("/index/check/checkIn")
    public Msg checkIn(@RequestBody ParkInfo data) {
        System.out.println(data);
        if(checkEntrance(data.getPlate())) {
            return Msg.success().add("Entrance", "false");
        }
        parkinfoservice.saveParkInfo(data);
        return Msg.success().add("Entrance", "true");
    }

    @RequestMapping( "/index/check/checkIn/checkHistory")
    public List<ParkForAll> showHistory(){
        List<ParkForAll> parkingHistoryPage = parkForAllService.findAllParkInForAll();
        return parkingHistoryPage;
    }


    @GetMapping
    @RequestMapping("/index/check/checkPlate")
    public Msg CheckPlate(@RequestBody ParkInfo data){
        ParkInfo parkInfo = parkinfoservice.findFirstByPlateOrderByEntrance(data.getPlate());
        Date exit = new Date();
        Date entrance = parkInfo.getEntrance();
        long parkingTime = calTimeDiffInMinutes(entrance, exit);
        float parkingFee = calParkingFee(parkingTime);
        return Msg.success().add("parkInfo", parkInfo).add("parkingFee", parkingFee).add("exit", exit);
    }

    @GetMapping
    @RequestMapping("/index/check/checkNum")
    public Msg CheckNum(@RequestBody ParkInfo data){
        ParkInfo parkInfo = parkinfoservice.findParkInfoByParkNum(data.getParkNum());
        return Msg.success().add("parkinfomation", parkInfo);
    }

    public boolean checkEntrance(String plate) {
        if(parkinfoservice.findParkInfoByPlate(plate) != null) {
            return true;
        }
        return false;
    }

    public String calTimeDiff(Date entrance, Date exit){
        long timeDiffMillis = exit.getTime() - entrance.getTime();

        long hours = TimeUnit.MILLISECONDS.toHours(timeDiffMillis);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis) - TimeUnit.HOURS.toMinutes(hours);
        String res = hours + " hours and " + minutes;
        return res;
    }

    public long calTimeDiffInMinutes(Date entrance, Date exit) {
        long timeDiffMillis = exit.getTime() - entrance.getTime();

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis);
        return minutes;
    }

    public float calParkingFee(long minutes) {
        return minutes / 60 * 2;
    }



}
