package com.cs673.backend.controller;

import com.cs673.backend.entity.Garage;
import com.cs673.backend.entity.MemberShip;
import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.service.*;
import com.cs673.backend.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class CheckController {
    @Autowired
    private ParkInfoService parkinfoservice;
    @Autowired
    private ParkForAllService parkForAllService;

    @Autowired
    private MembershipService membershipService;
    @Autowired
    private FeeService feeService;

    @Autowired
    private GarageService garageService;

    
    @PostMapping
    @RequestMapping("/index/check/checkIn")
    public Msg checkIn(@RequestBody ParkInfo data) {
        Garage garage = garageService.findGarageData();
        if(checkEntrance(data.getPlate())) {
            return Msg.success().add("Entrance", "false");
        }
        if(garage.getCurrent_spots() <= 0) {
            return Msg.fail();
        }
        garage.setCurrent_spots(garage.getCurrent_spots() - 1);
        garageService.save(garage);
        parkinfoservice.saveParkInfo(data);
        return Msg.success().add("Entrance", "true");
    }

    @RequestMapping("/index/check/checkOut")
    public Msg checkOut(@RequestBody ParkForAll data) {
        ParkInfo parkinfo = parkinfoservice.findFirstByPlateOrderByEntrance(data.getPlate());
        data.setCardNum(parkinfo.getCardNum());
        data.setEntrance(parkinfo.getEntrance());
        data.setCarType(parkinfo.getCarType());
        data.setParkNum(parkinfo.getParkNum());
        parkForAllService.save(data);
        parkinfoservice.deleteParkInfoByPlate(data.getPlate());

        //Add 1 to current spot
        Garage garage = garageService.findGarageData();
        garage.setCurrent_spots(garage.getCurrent_spots()+1);
        garageService.save(garage);

        return Msg.success();
    }
    // 车牌没有membership怎么办？
    public boolean checkOverdue(ParkInfo data){
        MemberShip membership = membershipService.findMembershipByPlate(data.getPlate());
        if(membership!=null) {
            Date endTime = membership.getEndTime();
            Date now = new Date();
            int result = endTime.compareTo(now);
            if (result <= 0) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }
    @ResponseBody
    @GetMapping
    @RequestMapping("/index/check/checkPlate")
    public Msg CheckPlate(@RequestBody ParkInfo data){
        ParkInfo parkInfo = parkinfoservice.findFirstByPlateOrderByEntrance(data.getPlate());
        Date now = new Date();
        Date entrance = parkInfo.getEntrance();
        long parkingTime = calTimeDiffInMinutes(entrance, now);
        BigDecimal parkingFee;
        if(false && checkOverdue(data)){
            parkingFee= BigDecimal.valueOf(0);
        }
        else {
            parkingFee = feeService.getParkingFee(parkingTime, "Car");
        }
        return Msg.success().add("parkinfo", parkInfo).add("parking_time", parkingTime).add("parkingFee", parkingFee).add("exit", now);
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

    //用出库时间检查已经出去的车辆。使用数据库parkforall。
    @GetMapping
    @RequestMapping("/index/check/checkIn/checkHistory/FindbyDate_Exit")
    public List<ParkForAll> FindbyDate_Exit(@RequestParam("myParam1") String startDate, @RequestParam("myParam2") String endDate) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startdate = formatter.parse(startDate);
        Date enddate = formatter.parse(endDate);
        System.out.println(startdate);
        List<ParkForAll> parkForAll = parkForAllService.findParkForAllByEntranceAndExitBetween(startdate, enddate);
        System.out.println(parkForAll);
        return parkForAll;
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
