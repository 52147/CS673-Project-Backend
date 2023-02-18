package com.cs673.backend.ServiceImp;

import com.cs673.backend.DAO.ParkInfoDao;
import com.cs673.backend.DTO.FormData;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.service.ParkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
@Service
public class ParkInfoServiceImp implements ParkInfoService {

    @Override
    public void saveParkInfo(FormData data) {
        Date entrance = new Date();
        ParkInfo parkInfo = new ParkInfo();
        parkInfo.setParkNum(data.getParkNum());
        parkInfo.setPlate(data.getPlate());
        parkInfo.setEntrance(entrance);
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

