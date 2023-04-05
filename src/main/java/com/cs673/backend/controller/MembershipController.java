package com.cs673.backend.controller;


import com.cs673.backend.entity.MemberFeeStandard;
import com.cs673.backend.entity.MemberShip;
import com.cs673.backend.service.MemberFeeService;
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

  @Autowired
  private MemberFeeService memberFeeService;

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
  public MemberShip checkPermitByPlate(@RequestBody MemberShip memberShip){
    return membershipService.findMembershipByPlate(memberShip.getPlate());
  }

  @PostMapping
  @RequestMapping("/check/index/check/checkIn/checkPermitByUserId")
  public MemberShip checkPermitByUserId(@RequestBody MemberShip memberShip){
    return membershipService.findMemberShipByUserId(memberShip.getUserId());
  }

  @PostMapping
  @RequestMapping("/check/index/check/checkIn/changeMemberPrice")
  public void changeMemberPrice(@RequestBody MemberFeeStandard memberFeeStandard){
    MemberFeeStandard tmpStandard = memberFeeService.findMemberFeeStandardById(1);
    if(memberFeeStandard.getMonthlyPay()!=tmpStandard.getMonthlyPay()){
      tmpStandard.setMonthlyPay(memberFeeStandard.getMonthlyPay());
    }
    if(memberFeeStandard.getYearlyPay()!=tmpStandard.getYearlyPay()){
      tmpStandard.setYearlyPay(memberFeeStandard.getYearlyPay());
    }
    memberFeeService.save(tmpStandard);
  }

  @PostMapping
  @RequestMapping("/check/index/check/checkIn/showMemberPrice")
  public MemberFeeStandard showMemberPrice(){
    return memberFeeService.findMemberFeeStandardById(1);
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
