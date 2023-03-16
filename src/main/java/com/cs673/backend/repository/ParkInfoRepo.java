package com.cs673.backend.repository;

import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface ParkInfoRepo extends JpaRepository<ParkInfo, Integer> {
    Optional<ParkInfo> findByPlate(String plate);
    //public void save(ParkInfo parkinfo);
    ParkInfo findFirstByPlateOrderByEntrance(String plate);


    ParkInfo findParkInfoByParkNum(int parkNum);

    ParkInfo findParkInfoByDate_Entrance(Date entrance);

    ParkInfo findParkInfoByPlate(String plate);

    void deleteParkInfoByPlate(String plate);
}
