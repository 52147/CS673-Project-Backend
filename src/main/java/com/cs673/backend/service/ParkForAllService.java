package com.cs673.backend.service;

import com.cs673.backend.entity.ParkForAll;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface ParkForAllService {

    Page<ParkForAll> findAllParkInForAll(Pageable pageable);

    void save(ParkForAll parkForAll);

    //AllData findById(int id);


    Page<ParkForAll> findAllByOrderByIdDesc(PageRequest id);


}
