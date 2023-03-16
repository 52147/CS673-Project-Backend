package com.cs673.backend.controller;

import com.cs673.backend.entity.FeeManage;
import com.cs673.backend.service.FeeService;
import com.cs673.backend.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FeeController {

  @Autowired
  private FeeService feeService;


  //  @PostMapping
//  @RequestMapping( "/check/index/check/checkIn/checkPrice")
//  public FeeManage showPrice(@RequestParam String carType){
//    FeeManage parkFee = feeService.findFeeManageByCarType(carType);
//    return parkFee;
//  }
  @PostMapping
  @RequestMapping( "/check/index/check/checkIn/checkPrice")
  public Msg showPrice(@RequestBody FeeManage carType){
    FeeManage parkFee = feeService.findFeeManageByCarType(carType.getCarType());
    return Msg.success()
            .add("id",parkFee.getId())
            .add("hour",parkFee.getHour())
            .add("carType",parkFee.getCarType())
            .add("firstPrice",parkFee.getFirstPrice())
            .add("secondPrice",parkFee.getSecondPrice())
            .add("maxPrice",parkFee.getMaxPrice())
            .add("comment",parkFee.getComment());
  }

  @RequestMapping( "/check/index/check/checkIn/changePrice")
  public void changePrice(@RequestBody FeeManage car) {
    FeeManage parkFee = feeService.findFeeManageByCarType(car.getCarType());
    parkFee.setMaxPrice(car.getMaxPrice());
    parkFee.setFirstPrice(car.getFirstPrice());
    parkFee.setSecondPrice(car.getSecondPrice());
    parkFee.setHour(car.getHour());
    parkFee.setCarType(car.getCarType());
  }


}