package com.cs673.backend.controller;

import com.cs673.backend.DTO.FormData;
import com.cs673.backend.entity.ParkForAll;
import com.cs673.backend.entity.ParkInfo;
import com.cs673.backend.service.ParkForAllService;
import com.cs673.backend.service.ParkInfoService;
import com.cs673.backend.utils.Msg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/index/check/checkIn")
public class CheckController {
    @Autowired
    private ParkInfoService parkInfoService;

    @Autowired
    private ParkForAllService parkForAllService;


    @ResponseBody
    @RequestMapping( "/index/check/checkIn/checkHistory")
    public Msg showHistory(@RequestParam("page") int page, @RequestParam("pageSize") int pageSize){
        PageRequest pageable = PageRequest.of(page, pageSize, Sort.Direction.DESC, "id");
        Page<ParkForAll> parkingHistoryPage = parkForAllService.findAllParkInForAll(pageable);
        return Msg.success();
    }
    @ResponseBody
    @RequestMapping("/index/check/checkIn")
    public Msg checkIn(FormData data){
        parkInfoService.saveParkInfo(data);
//      parkSpaceService.changeStatus(data.getId(), 1);
        return Msg.success();
    }

    @ResponseBody
    @RequestMapping("/index/check/checkout")
    public Msg CheckOut(FormData data){
        ParkInfo parkInfo= parkInfoService.findFirstByPlateOrderByEntrance(data.getPlate());
        return Msg.success().add("parkInfo",parkInfo);
    }
}
