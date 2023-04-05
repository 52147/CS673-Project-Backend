package com.cs673.backend.controller;


import com.cs673.backend.entity.MemberShip;
import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.service.MembershipService;
import com.cs673.backend.service.ParkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class MembershipController {

  @Autowired
  private MembershipService membershipService;

  private ParkInfoService parkInfoService;

  @RequestMapping("/check/index/check/checkIn/purchasePermit/Successful")
  public void purchasePermitSuccessful(@RequestBody MemberShip memberShip){
    membershipService.save(memberShip);
  }

  @RequestMapping( "/index/check/checkIn/checkMemberHistory")
  public List<MemberShip> showMember(){
    List<MemberShip> memberShips = membershipService.findAllMemberShip();
    return memberShips;
  }

  @PostMapping
  @RequestMapping("/check/index/check/checkIn/checkPermitByPlate")
  public MemberShip checkPermitByPlate(@RequestParam String plate){
    return membershipService.findMembershipByPlate(plate);
  }

  @PostMapping
  @RequestMapping("/check/index/check/checkIn/checkPermitByUserId")
  public MemberShip checkPermitByUserId(@RequestParam("p1") String userId){
    return membershipService.findMemberShipByUserId(userId);
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

}
