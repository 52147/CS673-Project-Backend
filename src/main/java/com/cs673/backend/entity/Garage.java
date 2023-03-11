package com.cs673.backend.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Garage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int total_spots;
    @Column
    private int current_sppots;
    @OneToOne
    private billtable billtable;
}
