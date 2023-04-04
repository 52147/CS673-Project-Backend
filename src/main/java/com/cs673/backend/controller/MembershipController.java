package com.cs673.backend.controller;


import com.cs673.backend.entity.MemberShip;
import com.cs673.backend.service.MembershipService;
import com.cs673.backend.service.ParkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MembershipController {

  @Autowired
  private MembershipService membershipService;

  private ParkInfoService parkInfoService;
  @PostMapping
  @RequestMapping("/check/index/check/checkIn/purchasePermit")
  public int purchasePermit(@RequestBody MemberShip memberShip){
    int month = calTimeDiffInMonth(memberShip.getStartTime(),memberShip.getEndTime());
    return calMemberFee(month);
  }

  @RequestMapping("/check/index/check/checkIn/purchasePermit/Successful")
  public void purchasePermitSuccessful(@RequestBody MemberShip memberShip){
    membershipService.save(memberShip);
  }


  @PostMapping
  @RequestMapping("/check/index/check/checkIn/checkPermitByPlate")
  public MemberShip checkPermitByPlate(String plate){
    return membershipService.findMembershipByPlate(plate);
  }

  @PostMapping
  @RequestMapping("/check/index/check/checkIn/checkPermitById")
  public MemberShip checkPermitByUserId(String id){
    return membershipService.findMemberShipByUserID(id);
  }

  public int calTimeDiffInMonth(Date date1, Date date2){
    Calendar cal1 = Calendar.getInstance();
    cal1.setTime(date1);
    Calendar cal2 = Calendar.getInstance();
    cal2.setTime(date2);
    // Calculate the difference in months
    int months = 0;
    int yearDiff = cal2.get(Calendar.YEAR) - cal1.get(Calendar.YEAR);
    int monthDiff = cal2.get(Calendar.MONTH) - cal1.get(Calendar.MONTH);
    months = yearDiff * 12 + monthDiff;
    return months;
  }

  public int calMemberFee(int month){
    if(month<6){
      return 200*month;
    }
    else if(month>=6 && month<12){
      return 180*month;
    }
    else if(month==12){
      return 1500;
    }
    else{
      return 100*month;
    }
  }
}
