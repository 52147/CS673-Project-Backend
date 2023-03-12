package com.cs673.backend.service;

import com.cs673.backend.DTO.FeeData;
import com.cs673.backend.entity.FeeManage;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;

import java.util.List;
public interface FeeService {
    public List<FeeManage> findFeeByCar(String carType);
}
