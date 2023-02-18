package com.cs673.backend.entity;

import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;
@Component
public class ParkInfo implements Serializable {
    private int id;
    private int parkNum;
    private String cardNum;
    private String plate;
    private Date entrance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParkNum() {
        return parkNum;
    }

    public void setParkNum(int parkNum) {
        this.parkNum = parkNum;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Date getEntrance() {
        return entrance;
    }

    public void setEntrance(Date entrance) {
        this.entrance = entrance;
    }

    @Override
    public String toString() {
        return "ParkInfo [id=" + id + ", parkNum=" + parkNum + ", cardNum=" + cardNum + ", plate=" + plate + ", entrance=" + entrance +  "]";
    }
}
