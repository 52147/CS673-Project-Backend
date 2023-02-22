package com.cs673.backend.serviceImp;

import com.cs673.backend.DTO.FormData;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.repository.ParkInfoRepo;
import com.cs673.backend.service.ParkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class ParkInfoServiceImp implements ParkInfoService {

    @Autowired
    private ParkInfoRepo parkInfoRepo;
    @Override
    public void saveParkInfo(ParkInfo data) {
        Date entrance = new Date();
        data.setEntrance(entrance);
        parkInfoRepo.save(data);
    }

    @Override
    public ParkInfo findParkInfoByParknum(int parkNum) {
        return null;
    }

    @Override
    public void deleteParkInfoByParkNum(int parkNum) {
        return;
    }
}

