package com.cs673.backend.controller;

import com.cs673.backend.DTO.FormData;

import com.cs673.backend.entity.FeeManage;
import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.repository.ParkInfoRepo;
import com.cs673.backend.repository.FeeRepo;
import com.cs673.backend.service.FeeService;
import com.cs673.backend.service.ParkForAllService;
import com.cs673.backend.service.ParkInfoService;
import com.cs673.backend.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

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
    
    @ResponseBody
    @GetMapping
    @RequestMapping("/index/check/checkPlate")
    public Msg CheckPlate(@RequestBody ParkInfo data){
        ParkInfo parkInfo = parkinfoservice.findFirstByPlateOrderByEntrance(data.getPlate());
        return Msg.success().add("parkinfo", parkInfo);
    }

    @RequestMapping( "/index/check/checkIn/checkHistory")
    public List<ParkForAll> showHistory(){
        List<ParkForAll> parkingHistoryPage = parkForAllService.findAllParkInForAll();
        return parkingHistoryPage;
    }

    @ResponseBody
    @GetMapping
    @RequestMapping("/index/check/checkIn/checkHistory/checkPlate")
    public Msg CheckAllPlate(@RequestBody ParkForAll data){
        ParkForAll parkForAll = parkForAllService.findParkForAllByPlate(data.getPlate());
        return Msg.success().add("parkforall", parkForAll);
    }

    //@RequestParam("name") String name),
    //用出库时间检查已经出去的车辆。使用数据库parkforall。
    @GetMapping
    @RequestMapping("/index/check/checkIn/checkHistory/FindbyDate_Exit")
    public Msg FindbyDate_Exit(@RequestParam("myParam1") String startDate, @RequestParam("myParam2") String endDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startdate = formatter.parse(startDate);
        Date enddate = formatter.parse(endDate);
        System.out.println(startdate);
        List<ParkForAll> parkForAll = parkForAllService.findParkForAllByEntranceAndExitBetween(startdate, enddate);
        System.out.println(parkForAll);
//        Date exit = parkForAll.getExit();
//        Date entrance = parkForAll.getEntrance();
        return Msg.success().add("parkForAll", parkForAll);
    }

    //用出库时间检查已经出去的车辆。使用数据库parkforall。
    @GetMapping
    @RequestMapping("/index/check/checkIn/checkHistory/FindbyDate_Entrance_Done")
    public Msg FindbyDate_Entrance_Done(@RequestBody ParkForAll data){
        ParkForAll parkForAll = parkForAllService.findParkForAllByExit(data.getExit());
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
