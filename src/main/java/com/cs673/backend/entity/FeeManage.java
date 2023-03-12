package com.cs673.backend.entity;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "feeManage")
public class FeeManage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String carType;

    @Column
    private float time;

    @Column
    private float firstPrice;

    @Column
    private float secondPrice;

    @Column
    private float maxPrice;

    @Column
    private String comment;


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
