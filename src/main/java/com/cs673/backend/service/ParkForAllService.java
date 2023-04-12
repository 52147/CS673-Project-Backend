package com.cs673.backend.service;

import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface ParkForAllService {

    List<ParkForAll> findAllParkInForAll();

    void save(ParkForAll parkinfo);

    //AllData findById(int id);


    Page<ParkForAll> findAllByOrderByIdDesc(PageRequest id);


    public ParkForAll findParkForAllByPlate(String plate);

    public List<ParkForAll> findAllParkForAllByPlate(String plate);

    public ParkForAll findParkForAllByEntrance(Date entrance);

    public ParkForAll findParkForAllByExit(Date exit);

    ParkForAll findParkForAllByEntranceAndExit(Date startDate, Date endDate);

    List<ParkForAll> findParkForAllByEntranceAndExitBetween(Date startDate, Date endDate);
}
