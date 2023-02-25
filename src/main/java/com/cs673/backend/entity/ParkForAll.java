package com.cs673.backend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
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
    @Column(name="plate")
    private String plate;
    @Column(name="parkIn")
    private Date parkIn;
    @Column(name="parkOut")

    private Date parkOut;


    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

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

}
