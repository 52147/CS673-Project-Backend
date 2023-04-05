package com.cs673.backend.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs673.backend.entity.Appointment;
import com.cs673.backend.mapper.AppointmentMapper;
import com.cs673.backend.service.AppointmentService;
import org.springframework.stereotype.Service;

@Service
public class AppointmentServiceImp extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {
}
