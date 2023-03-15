package com.cs673.backend.entity;

import javax.persistence.*;
import java.io.Serializable;
@Entity
public class BillTable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int hour;
    private String car_type;
    private int first_price;
    private int second_price;
    private int max_price;
    private String comment;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id")
    private Garage garage;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public int getFirst_price() {
        return first_price;
    }

    public void setFirst_price(int first_price) {
        this.first_price = first_price;
    }

    public int getSecond_price() {
        return second_price;
    }

    public void setSecond_price(int second_price) {
        this.second_price = second_price;
    }

    public int getMax_price() {
        return max_price;
    }

    public void setMax_price(int max_price) {
        this.max_price = max_price;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
