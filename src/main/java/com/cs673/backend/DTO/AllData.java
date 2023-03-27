package com.cs673.backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllData {
    private int id;
    private String cardNum;
    private String parkNum;
    private String carNum;
    private String parkIn;
    private String parkOut;
    private int payAmount;
    private String carType;
    public AllData(@JsonProperty("id") int id,
                   @JsonProperty("carType") String carType,
                   @JsonProperty("carNum") String cardNum,
                   @JsonProperty("parkNum") String parkNum,
                   @JsonProperty("parkIn") String parkIn,
                   @JsonProperty("parkOut")String parkOut,
                   @JsonProperty("paymentAmount")int payAmount
                      ) {
        this.id = id;
        this.carNum = cardNum;
        this.parkNum = parkNum;
        this.parkIn = parkIn;
        this.parkOut=parkOut;
        this.payAmount = payAmount;
        this.carType =carType;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCarType(){return this.carType;}
    public void setCarType(String carType){this.carType=carType;}
    public String getCarNum() {
        return carNum;
    }
    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }
    public String getParkNum() {
        return parkNum;
    }
    public void setParkNum(String parkNum) {
        this.parkNum = parkNum;
    }

    public String getParkIn() {
        return parkIn;
    }
    public void setParkIn(String parkIn) {
        this.parkIn = parkIn;
    }
    public String getParkOut() {
        return parkOut;
    }
    public void setParkOut(String parkOut) {
        this.parkOut = parkOut;
    }

    public void setPayAmount(int payAmount){
        this.payAmount = payAmount;
    }
    public int getPayAmount(){
        return this.payAmount;
    }
}