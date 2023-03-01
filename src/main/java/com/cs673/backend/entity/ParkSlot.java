package com.cs673.backend.entity;

import java.io.Serializable;
import java.util.ArrayList;

public class ParkSlot implements Serializable {
    private Integer id;
    private int parkId;
    /** empty: 0, occupied: 1**/
    private int status;
    /** Normal: 0 Priority Parking(Disabilities): 1**/
    private int tag;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getParkId() {
        return parkId;
    }

    public void setParkId(int parkId) {
        this.parkId = parkId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

}
