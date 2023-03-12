package com.cs673.backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class FeeData {

    private int id;
    private String carType;
    private float time;
    private float firstPrice;
    private float secondPrice;
    private float maxPrice;
    private String comment;
    public FeeData(@JsonProperty("id") int id,
                    @JsonProperty("carType") String carType,
                    @JsonProperty("time")float time,
                    @JsonProperty("firstPrice") float firstPrice,
                    @JsonProperty("secondPrice") float secondPrice,
                    @JsonProperty("maxPrice") float maxPrice,
                    @JsonProperty("comment") String comment
                    ) {
        this.id = id;
        this.carType = carType;
        this.time = time;
        this.firstPrice = firstPrice;
        this.secondPrice = secondPrice;
        this.maxPrice = maxPrice;
        this.comment = comment;
    }
    public void setCarType(String carType) {this.carType = carType;}
    public String getCarType(){return this.carType;}

    public void setTime(float time){this.time=time;}
    public float getTime(){return this.time;}

    public void setFirstPrice(float price){this.firstPrice=price;}
    public float getFirstPrice(){return this.firstPrice;}

    public void setSecondPrice(float price){this.secondPrice=price;}
    public float getSecondPrice(){return this.secondPrice;}

    public void setMaxPrice(float price){this.maxPrice=price;}
    public float getMaxPrice(){return this.maxPrice;}
    public void setComment(String comment){this.comment=comment;}
    public String getComment(){return this.comment;}

    public void setId(int id) {this.id = id;}

    public int getId() {return id;}
}
