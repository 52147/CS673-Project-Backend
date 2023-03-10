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

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    @RequestMapping("/index/check/checkOut")
    public Msg checkOut(@RequestBody ParkInfo data) {
        ParkInfo parkinfo = parkinfoservice.findFirstByPlateOrderByEntrance(data.getPlate());

        //Transfer parkinfo to parkforall
        parkForAllService.save(parkinfo);
        parkinfoservice.deleteParkInfoByPlate(data.getPlate());
        return Msg.success();
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
        BigDecimal parkingFee = calParkingFee(parkingTime);
        return Msg.success().add("parkInfo", parkInfo).add("parkingFee", parkingFee).add("exit", exit).add("parkingtime", parkingtimeToString(parkingTime));
    }

    //用入库时间检查还没出去的车。使用数据库parkinfo。
    @GetMapping
    @RequestMapping("/index/check/checkIn/checkHistory/FindbyDate_Entrance")
    public Msg FindbyDate_Entrance(@RequestBody ParkInfo data){
        ParkInfo parkInfo = parkinfoservice.findParkInfoByDate_Entrance(data.getEntrance());
        Date now = new Date();
        Date entrance = parkInfo.getEntrance();
        long parkingTime = calTimeDiffInMinutes(entrance, now);
        BigDecimal parkingFee = calParkingFee(parkingTime);
        return Msg.success().add("parkInfo", parkInfo).add("parkingFee", parkingFee).add("now", now).add("parkingtime", parkingtimeToString(parkingTime));
    }

    //用出库时间检查已经出去的车辆。使用数据库parkforall。
    @GetMapping
    @RequestMapping("/index/check/checkIn/checkHistory/FindbyDate_Exit")
    public Msg FindbyDate_Exit(@RequestBody ParkForAll data){
        ParkForAll parkForAll = parkForAllService.findParkForAllByDate_Entrance(data.getEntrance());
        Date exit = parkForAll.getExit();
        Date entrance = parkForAll.getEntrance();
        long parkingTime = calTimeDiffInMinutes(entrance, exit);
        BigDecimal parkingFee = calParkingFee(parkingTime);
        return Msg.success().add("parkForAll", parkForAll).add("parkingFee", parkingFee).add("exit", exit).add("parkingtime", parkingtimeToString(parkingTime));
    }

    //用出库时间检查已经出去的车辆。使用数据库parkforall。
    @GetMapping
    @RequestMapping("/index/check/checkIn/checkHistory/FindbyDate_Entrance_Done")
    public Msg FindbyDate_Entrance_Done(@RequestBody ParkForAll data){
        ParkForAll parkForAll = parkForAllService.findParkForAllByDate_Exit(data.getExit());
        Date exit = parkForAll.getExit();
        Date entrance = parkForAll.getEntrance();
        long parkingTime = calTimeDiffInMinutes(entrance, exit);
        BigDecimal parkingFee = calParkingFee(parkingTime);
        return Msg.success().add("parkForAll", parkForAll).add("parkingFee", parkingFee).add("exit", exit).add("parkingtime", parkingtimeToString(parkingTime));
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


    public long calTimeDiffInMinutes(Date entrance, Date exit) {
        long timeDiffMillis = exit.getTime() - entrance.getTime();

        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffMillis);
        return minutes;
    }

    public BigDecimal calParkingFee(long minutes) {
        return new BigDecimal(minutes).divide(new BigDecimal(60), RoundingMode.DOWN).multiply(new BigDecimal(2));
    }

    public String parkingtimeToString(long minutes) {
        long hours = minutes / 60;
        long minute = minutes % 60;
        return hours + " hours and " + minute + " minutes";
    }



}
