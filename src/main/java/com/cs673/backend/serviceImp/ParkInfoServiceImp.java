package com.cs673.backend.serviceImp;

import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.repository.ParkInfoRepo;
import com.cs673.backend.service.ParkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service
@Transactional
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
    public ParkInfo findParkInfoByParkNum(int parkNum) {
        return parkInfoRepo.findParkInfoByParkNum(parkNum);
    }

    @Override
    public ParkInfo findParkInfoByPlate(String plate) {
        return parkInfoRepo.findParkInfoByPlate(plate);
    }

    @Override
    public ParkInfo findParkInfoByDate_Entrance(Date entrance) {
        return parkInfoRepo.findParkInfoByDate_Entrance(entrance);
    }

    @Override
    public void deleteParkInfoByPlate(String plate) {
        parkInfoRepo.deleteParkInfoByPlate(plate);
    }
    @Override
    public ParkInfo findFirstByPlateOrderByEntrance(String plate){
        return parkInfoRepo.findFirstByPlateOrderByEntrance(plate);
    }

}

