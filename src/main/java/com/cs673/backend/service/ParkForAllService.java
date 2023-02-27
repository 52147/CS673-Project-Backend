package com.cs673.backend.service;

import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ParkForAllService {

    List<ParkForAll> findAllParkInForAll();

    void save(ParkInfo parkinfo);

    //AllData findById(int id);


    Page<ParkForAll> findAllByOrderByIdDesc(PageRequest id);


}
