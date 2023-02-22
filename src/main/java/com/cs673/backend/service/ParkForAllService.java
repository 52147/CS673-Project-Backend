package com.cs673.backend.service;

import com.cs673.backend.DTO.AllData;
import com.cs673.backend.entity.ParkForAll;

import java.util.List;
public interface ParkForAllService {

    List<AllData> findAllParkInForAll(int page, int size);

    void save(ParkForAll parkForAll);

    AllData findById(int id);

    int findAllParkInForAllCount(String name);

    List<AllData> findAllParkInForAllByLike(int i, int pageSize, String name);

    List<AllData> findByCardNum(String cardNum,String name);

    void updateCardNum(String cardNum, String cardNumNew);

    List<AllData> findByCardNumByPage(int page, int size, String cardNum, String name);
}
