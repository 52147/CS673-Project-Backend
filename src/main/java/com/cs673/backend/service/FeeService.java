package com.cs673.backend.service;

import com.cs673.backend.DTO.FeeData;
import com.cs673.backend.entity.FeeManage;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
public interface FeeService {
    public FeeManage findFeeManageByCarType(String carType);
    public void save(FeeManage feeManage);

    public BigDecimal getParkingFee(long parkingTime, String car_type);

}
