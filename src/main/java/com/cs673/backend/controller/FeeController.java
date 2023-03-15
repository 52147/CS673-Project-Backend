package com.cs673.backend.controller;

import com.cs673.backend.entity.FeeManage;
import com.cs673.backend.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class FeeController {

  @Autowired
  private FeeService feeService;


  @GetMapping( "/check/index/check/checkIn/checkPrice")
  public List<FeeManage> showPrice(@RequestParam String carType){
    List<FeeManage> parkFee = feeService.findFeeByCarType(carType);
    return parkFee;
  }

  @PostMapping ( "/check/index/check/checkIn/checkManage")
  public void save(@RequestParam String carType){

  }
}
