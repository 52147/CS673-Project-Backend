package com.cs673.backend.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CheckData {
    private String plate;
    private Boolean Entrance;

    public CheckData(@JsonProperty("plate") String plate,
                     @JsonProperty("Entrance") Boolean Entrance) {
        this.plate = plate;
        this.Entrance = Entrance;
    }

    public String getPlate() {
        return plate;
    }

    public void setPlate(String plate) {
        this.plate = plate;
    }

    public Boolean getEntrance() {
        return Entrance;
    }

    public void setEntrance(Boolean entrance) {
        Entrance = entrance;
    }
}
