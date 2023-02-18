package com.cs673.backend.service;

import com.cs673.backend.DTO.FormData;
import com.cs673.backend.entity.ParkInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

public interface ParkInfoService {
    public void saveParkInfo(FormData data);
    public ParkInfo findParkInfoByParknum(int parkNum);
    public void deleteParkInfoByParkNum(int parkNum);
}

