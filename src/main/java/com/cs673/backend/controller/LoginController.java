package com.cs673.backend.controller;

import com.cs673.backend.entity.ParkSlot;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("student")
public class LoginController {
    @GetMapping
    public List<ParkSlot> getallparking() {
        ParkSlot park1 = new ParkSlot();
        park1.setId(1);
        List<ParkSlot> lst = new ArrayList<ParkSlot>();
        lst.add(park1);
        return lst;
    }
}
