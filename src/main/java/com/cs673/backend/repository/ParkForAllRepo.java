package com.cs673.backend.repository;

import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.Optional;

public interface ParkForAllRepo extends JpaRepository<ParkForAll, Integer> {
    public Page<ParkForAll> findAllByOrderByIdDesc(@Param("Id") PageRequest id) ;

    Optional<ParkForAll> findByPlate(String plate);

    ParkForAll findParkForAllByEntrance(Date entrance);

    ParkForAll findParkForAllByExit(Date exit);

    public ParkInfo findParkInfoByParkNum(@Param("parkNum")int parkNum);
    public void deleteParkInfoByParkNum(@Param("parkNum")int parkNum);
}
