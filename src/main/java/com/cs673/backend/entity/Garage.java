package com.cs673.backend.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "garage")
public class Garage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int total_spots;
    @Column
    private int current_spots;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTotal_spots() {
        return total_spots;
    }

    public void setTotal_spots(int total_spots) {
        this.total_spots = total_spots;
    }

    public int getCurrent_spots() {
        return current_spots;
    }

    public void setCurrent_spots(int current_spots) {
        this.current_spots = current_spots;
    }

}
