package com.cs673.backend.serviceImp;

import com.cs673.backend.service.FeeService;
import com.cs673.backend.entity.FeeManage;
import com.cs673.backend.repository.FeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class FeeServiceImp implements FeeService {
    @Autowired
    private FeeRepo feeRepo;

    @Override
    public FeeManage findFeeManageByCarType(String carType){ return feeRepo.findFeeManageByCarType(carType);}

    @Override
    public void save(FeeManage feeManage){
        FeeManage newFee = new FeeManage();
        newFee.setCarType(feeManage.getCarType());
        newFee.setHour(feeManage.getHour());
        newFee.setFirstPrice(feeManage.getFirstPrice());
        newFee.setSecondPrice(feeManage.getSecondPrice());
        newFee.setMaxPrice(feeManage.getMaxPrice());
        feeRepo.save(newFee);
    }

}
