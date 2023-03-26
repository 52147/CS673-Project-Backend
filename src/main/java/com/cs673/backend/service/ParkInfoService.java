package com.cs673.backend.service;

import com.cs673.backend.DTO.FormData;
import com.cs673.backend.entity.ParkInfo;

import java.util.Date;

public interface ParkInfoService {
    public void saveParkInfo(ParkInfo data);
    public ParkInfo findParkInfoByParkNum(int parkNum);

    public ParkInfo findParkInfoByPlate(String plate);

    public ParkInfo findParkInfoByEntrance(Date entrance);


    void deleteParkInfoByPlate(String plate);

    public ParkInfo findFirstByPlateOrderByEntrance(String plate);
}

