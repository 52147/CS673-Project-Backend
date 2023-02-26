package com.cs673.backend.service;

import com.cs673.backend.entity.ParkForAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ParkForAllService {

    List<ParkForAll> findAllParkInForAll();

    void save(ParkForAll parkForAll);

    //AllData findById(int id);


    Page<ParkForAll> findAllByOrderByIdDesc(PageRequest id);


}
