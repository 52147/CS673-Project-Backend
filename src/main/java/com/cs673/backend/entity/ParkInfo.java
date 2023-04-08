package com.cs673.backend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "parkinfo")
public class ParkInfo implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String carType;
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int parkNum;
    @Column(nullable = true)
    private String cardNum;
    @Column(nullable = false)
    private String plate;
    @Column(nullable = false)
    private Date entrance;
    @Column(columnDefinition = "boolean default false")
    private Boolean membership = false;
    @Column(columnDefinition = "boolean default false")
    private Boolean reservation = false;

    public Boolean getMembership() {
        return membership;
    }

    public void setMembership(Boolean membership) {
        this.membership = membership;
    }

    public Boolean getReservation() {
        return reservation;
    }

    public void setReservation(Boolean reservation) {
        this.reservation = reservation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarType(){return this.carType;}
    public void setCarType(String carType){this.carType=carType;}

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
