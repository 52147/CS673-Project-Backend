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
    public List<FeeManage> findFeeByCarType(String carType){ return feeRepo.findFeeByCarType(carType);}

    @Override
    public void save(String carType){feeRepo.save(carType);}

}
