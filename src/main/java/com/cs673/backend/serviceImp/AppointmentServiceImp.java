package com.cs673.backend.serviceImp;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs673.backend.entity.Appointment;
import com.cs673.backend.mapper.AppointmentMapper;
import com.cs673.backend.service.AppointmentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImp extends ServiceImpl<AppointmentMapper, Appointment> implements AppointmentService {
    @Resource
    private AppointmentMapper appointmentMapper;
    @Override
    public Boolean checkReservation(Date startDate, String plate) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        Date endDate = calendar.getTime();
        List<Appointment> appointments = appointmentMapper.getAppointmentsBetweenDatesAndPlate(startDate, endDate, plate);
        if(appointments != null) {
            return true;
        }
        return false;
    }
}
