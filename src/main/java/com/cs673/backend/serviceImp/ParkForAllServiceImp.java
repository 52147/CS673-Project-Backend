package com.cs673.backend.serviceImp;


import com.cs673.backend.repository.ParkForAllRepo;
import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.DTO.AllData;
import com.cs673.backend.service.ParkForAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

public class ParkForAllServiceImp implements  ParkForAllService{
    @Autowired
    private ParkForAllRepo parkForAllRepo;
//    public parkInfo findParkInfByParkNum(int parkNum) {
//        return parkForAllRepo.findParkInfoByParkNum(parkNum);
//    }
    public void save(ParkForAll parkForAll) {parkForAllRepo.save(parkForAll);}
    public AllData findById(int id) {
        return parkForAllRepo.findById(id);
    }

    public List<ParkinfoallData> findAllParkinfoallByLike(int page, int size, String name) {
        return parkForAllRepo.findAllParkinfoallByLike(page,size,name);
    }


}
