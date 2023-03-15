package com.cs673.backend.repository;


import com.cs673.backend.entity.FeeManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FeeRepo extends JpaRepository<FeeManage, Integer> {
    List<FeeManage> findFeeByCarType(String carType);

    void save(String carType);

}
