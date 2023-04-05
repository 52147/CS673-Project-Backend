package com.cs673.backend.serviceImp;

import com.cs673.backend.service.FeeService;
import com.cs673.backend.entity.FeeManage;
import com.cs673.backend.repository.FeeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class FeeServiceImp implements FeeService {
    @Autowired
    private FeeRepo feeRepo;

    @Override
    public FeeManage findFeeManageByCarType(String carType){ return feeRepo.findFeeManageByCarType(carType);}

    @Override
    public void save(FeeManage feeManage){
        feeRepo.save(feeManage);
    }

    @Override
    public BigDecimal getParkingFee(long parkingTime, String car_type) {
        float parking_fee;

        FeeManage charge_rate = feeRepo.findFeeManageByCarType(car_type);
        int total_hours = (int) Math.ceil(parkingTime / 60.0);
        if(total_hours <= charge_rate.getHour()) {
            parking_fee = charge_rate.getFirstPrice() * total_hours;
        }else {
            parking_fee = charge_rate.getFirstPrice() * charge_rate.getHour() + charge_rate.getSecondPrice() * (total_hours - charge_rate.getHour());
        }
        if(parking_fee > charge_rate.getMaxPrice()) {
            parking_fee = charge_rate.getMaxPrice();
        }
        return new BigDecimal(parking_fee);
    }


}
