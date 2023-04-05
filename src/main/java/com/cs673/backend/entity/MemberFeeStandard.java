package com.cs673.backend.entity;

import javax.persistence.*;

@Entity
@Table(name = "memberfee")
public class MemberFeeStandard {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @Column
  private int monthlyPay;

  @Column
  private int yearlyPay;
  public void setId(int id){this.id=id;}
  public int getId(){return this.id;}

  public void setMonthlyPay(int monthlyPay){
    this.monthlyPay = monthlyPay;
  }
  public int getMonthlyPay(){return this.monthlyPay;}

  public void setYearlyPay(int yearlyPay){this.yearlyPay=yearlyPay;}

  public int getYearlyPay(){return this.yearlyPay;}
}
