package com.cs673.backend.repository;

import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ParkForAllRepo extends JpaRepository<ParkForAll, Integer> {
    Optional<ParkForAll> findByPlate(String plate);
    public void save(ParkInfo parkInfo);
    public ParkInfo findParkInfoByParkNum(@Param("parkNum")int parkNum);
    public void deleteParkInfoByParkNum(@Param("parkNum")int parkNum);
}
