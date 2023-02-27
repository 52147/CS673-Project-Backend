package com.cs673.backend.service;

import com.cs673.backend.DTO.FormData;
import com.cs673.backend.entity.ParkInfo;

public interface ParkInfoService {
    public void saveParkInfo(ParkInfo data);
    public ParkInfo findParkInfoByParkNum(int parkNum);

    public ParkInfo findParkInfoByPlate(String plate);

    void deleteParkInfoByPlate(String plate);

    public ParkInfo findFirstByPlateOrderByEntrance(String plate);
}

