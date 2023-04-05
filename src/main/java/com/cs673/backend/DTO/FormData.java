package com.cs673.backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class FormData {
    private int id;
    private int parkNum;
    private String plate;

    private String cardNum;
    private Date entrance;
    private Date exit;
    private Integer tag;
    private int payId;
    private int payAmount;
    private String carType;

    public FormData(@JsonProperty("id") int id,
                    @JsonProperty("carType") String carType,
                    @JsonProperty("parkNum") int parkNum,
                    @JsonProperty("plate") String plate,
                    @JsonProperty("cardNum") String cardNum,
                    @JsonProperty("entrance") Date entrance,
                    @JsonProperty("exit") Date exit,
                    @JsonProperty("tag") Integer tag,
                    @JsonProperty("payId") int payId,
                    @JsonProperty("payAmount") int payAmount) {
        this.id = id;
        this.carType = carType;
        this.parkNum = parkNum;
        this.plate = plate;
        this.cardNum = cardNum;
        this.entrance = entrance;
        this.exit = exit;
        this.tag = tag;
        this.payId = payId;
        this.payAmount = payAmount;
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

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public int getPayId() {
        return payId;
    }

    public void setPayId(int payId) {
        this.payId = payId;
    }

    public int getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(int payAmount) {
        this.payAmount = payAmount;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    @Override
    public String toString() {
        return "FormData [id=" + id + ", park_Num=" + parkNum + ", card_Num=" + cardNum + ", plate=" + plate + ", entrance=" + entrance + ", exit=" + exit + ", tag=" + tag + "]";
    }
}
