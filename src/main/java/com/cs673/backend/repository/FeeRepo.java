package com.cs673.backend.repository;


import com.cs673.backend.entity.FeeManage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeeRepo extends JpaRepository<FeeManage, Integer> {
    FeeManage findFeeManageByCarType(String carType);

}
