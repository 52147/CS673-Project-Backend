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


  @PostMapping
  @RequestMapping( "/check/index/check/checkIn/checkPrice")
  public Msg showPrice(@RequestBody FeeManage carType){
    FeeManage parkFee = feeService.findFeeManageByCarType(carType.getCarType().toLowerCase());
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
    FeeManage parkFee = feeService.findFeeManageByCarType(car.getCarType().toLowerCase());
    if (car.getHour()!=0)
     parkFee.setHour(car.getHour());
    parkFee.setCarType(car.getCarType());
    if (car.getFirstPrice()!=0)
      parkFee.setFirstPrice(car.getFirstPrice());
    if(car.getSecondPrice()!=0)
      parkFee.setSecondPrice(car.getSecondPrice());
    if(car.getMaxPrice()!=0)
      parkFee.setMaxPrice(car.getMaxPrice());
    if(car.getComment()!=null)
      parkFee.setComment(car.getComment());
    feeService.save(parkFee);
  }


}