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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
    @Autowired
    private AppointmentService appointmentService;

    
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
        if(checkOverdue(data)) {
            data.setMembership(true);
        }
        if(checkReservation(data)) {
            data.setReservation(true);
        }
        garage.setCurrent_spots(garage.getCurrent_spots() - 1);
        garageService.save(garage);
        
        if(data.getCarType()=="Bicycle"){
            data.setPlate(getBicyclePlate());
        }
        parkinfoservice.saveParkInfo(data);
        return Msg.success().add("Entrance", "true");
    }

    /*
    根据当前时间生成临时车牌
    仅用于后端内部调用！
     */
    //@RequestMapping("/index/check/getBicyclePlate")
    public String getBicyclePlate(){
        LocalTime time = LocalTime.now(); // get the current time
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HHmmss");
        return time.format(formatter)+"Bicycle";
    }

    @RequestMapping("/index/check/checkOut")
    public Msg checkOut(@RequestBody ParkForAll data) {
        ParkInfo parkinfo = parkinfoservice.findFirstByPlateOrderByEntrance(data.getPlate());
        data.setCarType(parkinfo.getCarType());
        data.setCardNum(parkinfo.getCardNum());
        data.setEntrance(parkinfo.getEntrance());
        data.setParkNum(parkinfo.getParkNum());
        parkForAllService.save(data);
        parkinfoservice.deleteParkInfoByPlate(data.getPlate());
        //Add 1 to current spot
        Garage garage = garageService.findGarageData();
        garage.setCurrent_spots(garage.getCurrent_spots()+1);
        garageService.save(garage);
        return Msg.success();
    }
    //Check if plate is membership
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

    public boolean checkReservation(ParkInfo data) {
        return appointmentService.checkReservation(new Date(), data.getPlate());
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
        //If it is reserved or membership, we don't charge
        if(data.getMembership() || data.getReservation()){
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
    public List<ParkForAll> CheckAllPlate(@RequestBody ParkForAll data){
        List<ParkForAll> parkForAll = parkForAllService.findAllParkForAllByPlate(data.getPlate());
        return parkForAll;
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
