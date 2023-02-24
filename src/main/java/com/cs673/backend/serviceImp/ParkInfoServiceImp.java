package com.cs673.backend.serviceImp;

import com.cs673.backend.DTO.FormData;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.repository.ParkInfoRepo;
import com.cs673.backend.service.ParkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class ParkInfoServiceImp implements ParkInfoService {

    @Autowired
    private ParkInfoRepo parkInfoRepo;
    @Override
    public void saveParkInfo(FormData data) {
        Date entrance = new Date();
        ParkInfo parkInfo = new ParkInfo();
        parkInfo.setPlate(data.getPlate());
        parkInfo.setEntrance(entrance);
        parkInfoRepo.save(parkInfo);
    }

    @Override
    public ParkInfo findParkInfoByParknum(int parkNum) {
        return null;
    }

    @Override
    public void deleteParkInfoByParkNum(int parkNum) {
        return;
    }

    public ParkInfo findFirstByPlateOrderByEntrance(String plate){
        return parkInfoRepo.findFirstByPlateOrderByEntrance(plate);
    }
}

