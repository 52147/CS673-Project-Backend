package com.cs673.backend.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cs673.backend.entity.Appointment;
import com.cs673.backend.entity.Parklot;
import com.cs673.backend.entity.Appointment;
import com.cs673.backend.mapper.AppointmentMapper;
import com.cs673.backend.mapper.ParklotMapper;
import com.cs673.backend.service.AppointmentService;
import com.cs673.backend.service.ParklotService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/parklot")
public class ParklotController {
    @Resource
    private ParklotService parklotService;

    @Resource
    private AppointmentService appointmentService;

    @Resource
    private ParklotMapper parklotMapper;
    
    @Resource
    private AppointmentMapper appointmentMapper;

    //显示全部预约信息
    @GetMapping
    public List<Parklot> findAll() {
        return parklotService.list();
    }

    //分页查询
    @GetMapping("/page")
    public IPage<Parklot> findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize,
                                @RequestParam(defaultValue = "") String Type,
                                @RequestParam(defaultValue = "") String A1,
                                @RequestParam(defaultValue = "") String A2,
                                @RequestParam(defaultValue = "") String A3,
                                   @RequestParam(defaultValue = "") String A4,
                                   @RequestParam(defaultValue = "") String A5,
                                   @RequestParam(defaultValue = "") String A6,
                                   @RequestParam(defaultValue = "") String A7,
                                   @RequestParam(defaultValue = "") String A8,
                                   @RequestParam(defaultValue = "") String A9,
                                   @RequestParam(defaultValue = "") String A10,
                                   @RequestParam(defaultValue = "") String A11,
                                   @RequestParam(defaultValue = "") String A12,
                                   @RequestParam(defaultValue = "") String A13,
                                   @RequestParam(defaultValue = "") String A14,
                                   @RequestParam(defaultValue = "") String A15,
                                   @RequestParam(defaultValue = "") String A16,
                                   @RequestParam(defaultValue = "") String A17,
                                   @RequestParam(defaultValue = "") String A18,
                                   @RequestParam(defaultValue = "") String A19,
                                   @RequestParam(defaultValue = "") String A20,
                                   @RequestParam(defaultValue = "") String A21,
                                   @RequestParam(defaultValue = "") String A22,
                                   @RequestParam(defaultValue = "") String A23,
                                   @RequestParam(defaultValue = "") String A24,
                                   @RequestParam(defaultValue = "") String B1,
                                   @RequestParam(defaultValue = "") String B2,
                                   @RequestParam(defaultValue = "") String B3,
                                   @RequestParam(defaultValue = "") String B4,
                                   @RequestParam(defaultValue = "") String B5,
                                   @RequestParam(defaultValue = "") String B6,
                                   @RequestParam(defaultValue = "") String B7,
                                   @RequestParam(defaultValue = "") String B8,
                                   @RequestParam(defaultValue = "") String B9,
                                   @RequestParam(defaultValue = "") String B10,
                                   @RequestParam(defaultValue = "") String B11,
                                   @RequestParam(defaultValue = "") String B12,
                                   @RequestParam(defaultValue = "") String B13,
                                   @RequestParam(defaultValue = "") String B14,
                                   @RequestParam(defaultValue = "") String B15,
                                   @RequestParam(defaultValue = "") String B16,
                                   @RequestParam(defaultValue = "") String B17,
                                   @RequestParam(defaultValue = "") String B18,
                                   @RequestParam(defaultValue = "") String B19,
                                   @RequestParam(defaultValue = "") String B20,
                                   @RequestParam(defaultValue = "") String B21,
                                   @RequestParam(defaultValue = "") String B22,
                                   @RequestParam(defaultValue = "") String B23,
                                   @RequestParam(defaultValue = "") String B24) {
        IPage<Parklot> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Parklot> queryWrapper = new QueryWrapper<>();
        if (!"".equals(Type)) {
            queryWrapper.like("Type", Type);
        }
        if (!"".equals(A1)) {
            queryWrapper.like("A1", A1);
        }
        if (!"".equals(A2)) {
            queryWrapper.like("A2", A2);
        }
        if (!"".equals(A3)) {
            queryWrapper.like("A3", A3);
        }
        if (!"".equals(A4)) {
            queryWrapper.like("A4", A4);
        }
        if (!"".equals(A5)) {
            queryWrapper.like("A5", A5);
        }
        if (!"".equals(A6)) {
            queryWrapper.like("A6", A6);
        }
        if (!"".equals(A7)) {
            queryWrapper.like("A7", A7);
        }
        if (!"".equals(A8)) {
            queryWrapper.like("A8", A8);
        }
        if (!"".equals(A9)) {
            queryWrapper.like("A9", A9);
        }
        if (!"".equals(A10)) {
            queryWrapper.like("A10", A10);
        }
        if (!"".equals(A11)) {
            queryWrapper.like("A11", A11);
        }
        if (!"".equals(A12)) {
            queryWrapper.like("A12", A12);
        }
        if (!"".equals(A13)) {
            queryWrapper.like("A13", A13);
        }
        if (!"".equals(A14)) {
            queryWrapper.like("A14", A14);
        }
        if (!"".equals(A15)) {
            queryWrapper.like("A15", A15);
        }
        if (!"".equals(A16)) {
            queryWrapper.like("A16", A16);
        }
        if (!"".equals(A17)) {
            queryWrapper.like("A17", A17);
        }
        if (!"".equals(A18)) {
            queryWrapper.like("A18", A18);
        }
        if (!"".equals(A19)) {
            queryWrapper.like("A19", A19);
        }
        if (!"".equals(A20)) {
            queryWrapper.like("A20", A20);
        }
        if (!"".equals(A21)) {
            queryWrapper.like("A21", A21);
        }
        if (!"".equals(A22)) {
            queryWrapper.like("A22", A22);
        }
        if (!"".equals(A23)) {
            queryWrapper.like("A23", A23);
        }
        if (!"".equals(A24)) {
            queryWrapper.like("A24", A24);
        }

        if (!"".equals(B1)) {
            queryWrapper.like("B1", B1);
        }
        if (!"".equals(B2)) {
            queryWrapper.like("B2", B2);
        }
        if (!"".equals(B3)) {
            queryWrapper.like("B3", B3);
        }
        if (!"".equals(B4)) {
            queryWrapper.like("B4", B4);
        }
        if (!"".equals(B5)) {
            queryWrapper.like("B5", B5);
        }
        if (!"".equals(B6)) {
            queryWrapper.like("B6", B6);
        }
        if (!"".equals(B7)) {
            queryWrapper.like("B7", B7);
        }
        if (!"".equals(B8)) {
            queryWrapper.like("B8", B8);
        }
        if (!"".equals(B9)) {
            queryWrapper.like("B9", B9);
        }
        if (!"".equals(B10)) {
            queryWrapper.like("B10", B10);
        }
        if (!"".equals(B11)) {
            queryWrapper.like("B11", B11);
        }
        if (!"".equals(B12)) {
            queryWrapper.like("B12", B12);
        }
        if (!"".equals(B13)) {
            queryWrapper.like("B13", B13);
        }
        if (!"".equals(B14)) {
            queryWrapper.like("B14", B14);
        }
        if (!"".equals(B15)) {
            queryWrapper.like("B15", B15);
        }
        if (!"".equals(B16)) {
            queryWrapper.like("B16", B16);
        }
        if (!"".equals(B17)) {
            queryWrapper.like("B17", B17);
        }
        if (!"".equals(B18)) {
            queryWrapper.like("B18", B18);
        }
        if (!"".equals(B19)) {
            queryWrapper.like("B19", B19);
        }
        if (!"".equals(B20)) {
            queryWrapper.like("B20", B20);
        }
        if (!"".equals(B21)) {
            queryWrapper.like("B21", B21);
        }
        if (!"".equals(B22)) {
            queryWrapper.like("B22", B22);
        }
        if (!"".equals(B23)) {
            queryWrapper.like("B23", B23);
        }
        if (!"".equals(B24)) {
            queryWrapper.like("B24", B24);
        }
        return parklotService.page(page, queryWrapper);
    }

    // 更新
    @PostMapping("/save")
    public boolean save(@RequestBody Parklot parklot,
                        @RequestParam String date,
                        @RequestParam String license,
                        @RequestParam String name) {
        Appointment appointment = new Appointment();
        appointment.setDate(date);
        appointment.setParklot(parklot.getId());
        appointment.setName(name);
        appointment.setLicense(license);
        appointmentService.saveOrUpdate(appointment);
        return parklotService.saveOrUpdate(parklot);
    }

    //生成预约记录
    @PostMapping("/appointment")
    public boolean save(@RequestParam Integer parklotId,
                        @RequestParam String date,
                        @RequestParam String license,
                        @RequestParam String name) {
        Appointment appointment = new Appointment();
        appointment.setDate(date);
        appointment.setParklot(parklotId);
        appointment.setName(name);
        appointment.setLicense(license);
        return appointmentService.saveOrUpdate(appointment);
    }

    @GetMapping("/appointment/showAll")
    public List<Appointment> showAll() {
        return appointmentService.list();
    }

    @GetMapping("/appointment/showUser")
    public List<Appointment> showUser(@RequestParam String userName) {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("name", userName);
        return appointmentMapper.selectList(queryWrapper);
    }

    // 统计
    @GetMapping("/emptySpace")
    public long emptySpace(@RequestParam String T){
        QueryWrapper<Parklot> queryWrapper = new QueryWrapper<>();
        //这里T传入列名（Ai Bi）
        queryWrapper.eq(T, "EMPTY");
        return parklotService.count(queryWrapper);
    }

    // 重置
    @PostMapping("/reset")
    public void reset() {
        parklotService.reset();
        UpdateWrapper<Parklot> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("B1","EMPTY");
        updateWrapper.set("B2","EMPTY");
        updateWrapper.set("B3","EMPTY");
        updateWrapper.set("B4","EMPTY");
        updateWrapper.set("B5","EMPTY");
        updateWrapper.set("B6","EMPTY");
        updateWrapper.set("B7","EMPTY");
        updateWrapper.set("B8","EMPTY");
        updateWrapper.set("B9","EMPTY");
        updateWrapper.set("B10","EMPTY");
        updateWrapper.set("B11","EMPTY");
        updateWrapper.set("B12","EMPTY");
        updateWrapper.set("B13","EMPTY");
        updateWrapper.set("B14","EMPTY");
        updateWrapper.set("B15","EMPTY");
        updateWrapper.set("B16","EMPTY");
        updateWrapper.set("B17","EMPTY");
        updateWrapper.set("B18","EMPTY");
        updateWrapper.set("B19","EMPTY");
        updateWrapper.set("B20","EMPTY");
        updateWrapper.set("B21","EMPTY");
        updateWrapper.set("B22","EMPTY");
        updateWrapper.set("B23","EMPTY");
        updateWrapper.set("B24","EMPTY");
        parklotMapper.update(null,updateWrapper);
    }

}
