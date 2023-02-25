package com.cs673.backend.serviceImp;


import com.cs673.backend.repository.ParkForAllRepo;
import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.DTO.AllData;
import com.cs673.backend.service.ParkForAllService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.util.List;
@Service
public class ParkForAllServiceImp implements  ParkForAllService{
    @Autowired
    private ParkForAllRepo parkForAllRepo;
//    public parkInfo findParkInfByParkNum(int parkNum) {
//        return parkForAllRepo.findParkInfoByParkNum(parkNum);
//    }
    @Override
    public void save(ParkForAll parkForAll) {parkForAllRepo.save(parkForAll);}
    //@Override
    //public AllData findById(int id) {return parkForAllRepo.findById(id);}

    @Override
    public Page<ParkForAll> findAllParkInForAll(Pageable pageable){return parkForAllRepo.findAll(pageable);}

    @Override
    public Page<ParkForAll> findAllByOrderByIdDesc(PageRequest id){return parkForAllRepo.findAllByOrderByIdDesc(id);}

}
