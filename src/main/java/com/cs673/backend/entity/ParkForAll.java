package com.cs673.backend.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table(name = "parkforall")
public class ParkForAll implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String cardNum;
    @Column
    private int parkNum;
    @Column
    private String plate;
    @Column
    private Date entrance;
    @Column(name = "exit_time")
    private Date exit;

    @Column
    private BigDecimal parkingFee;

    @Column
    private long totalParkingTime;

    public BigDecimal getParkingFee() {
        return parkingFee;
    }

    public void setParkingFee(BigDecimal parkingFee) {
        this.parkingFee = parkingFee;
    }

    public long getTotalParkingTime() {
        return totalParkingTime;
    }

    public void setTotalParkingTime(long totalParkingTime) {
        this.totalParkingTime = totalParkingTime;
    }

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

    public Date getEntrance() {
        return entrance;
    }

    public void setEntrance(Date entrance) {
        this.entrance = entrance;
    }

    public Date getExit() {
        return exit;
    }

    public void setExit(Date exit) {
        this.exit = exit;
    }
}
