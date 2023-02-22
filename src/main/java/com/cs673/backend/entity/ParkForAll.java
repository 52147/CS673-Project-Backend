package com.cs673.backend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class ParkForAll implements Serializable {
    @Id
    @Column(name="Id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "cardNum")
    private String cardNum;
    @Column(name="parkNum")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parkNum;
    @Column(name="carNum")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String carNum;
    @Column(name="parkIn")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Date parkIn;
    @Column(name="parkOut")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Date parkOut;
    @Column(name="parkTemp")
    private int parkTemp;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public int getParkNum() {
        return parkNum;
    }

    public void setParkNum(int i) {
        this.parkNum = i;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public Date getParkIn() {
        return parkIn;
    }

    public void setParkIn(Date parkIn) {
        this.parkIn = parkIn;
    }

    public Date getParkOut() {
        return parkOut;
    }

    public void setParkOut(Date parkOut) {
        this.parkOut = parkOut;
    }

    public int getParkTemp() {
        return parkTemp;
    }

    public void setParkTemp(int parkTemp) {
        this.parkTemp = parkTemp;
    }


}
