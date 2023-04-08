package com.cs673.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs673.backend.entity.Appointment;

import java.util.Date;


public interface AppointmentService extends IService<Appointment> {
    Boolean checkReservation(Date date, String plate);
}
