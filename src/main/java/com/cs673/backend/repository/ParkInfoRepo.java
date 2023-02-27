package com.cs673.backend.repository;

import com.cs673.backend.entity.ParkInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkInfoRepo extends JpaRepository<ParkInfo, Integer> {
    Optional<ParkInfo> findByPlate(String plate);
    //public void save(ParkInfo parkinfo);
    ParkInfo findFirstByPlateOrderByEntrance(String plate);


    ParkInfo findParkInfoByParkNum(int parkNum);

    ParkInfo findParkInfoByPlate(String plate);
}
