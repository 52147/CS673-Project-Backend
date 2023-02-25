package com.cs673.backend.service;

import com.cs673.backend.DTO.FormData;
import com.cs673.backend.entity.ParkInfo;

public interface ParkInfoService {
    public void saveParkInfo(ParkInfo data);
    public ParkInfo findParkInfoByParknum(int parkNum);
    public void deleteParkInfoByParkNum(int parkNum);

    public ParkInfo findFirstByPlateOrderByEntrance(String plate);
}

