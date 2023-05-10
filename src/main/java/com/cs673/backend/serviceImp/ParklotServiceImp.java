package com.cs673.backend.serviceImp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cs673.backend.entity.Parklot;
import com.cs673.backend.mapper.ParklotMapper;
import com.cs673.backend.service.ParklotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParklotServiceImp extends ServiceImpl<ParklotMapper, Parklot> implements ParklotService {
    @Override
    public void reset() {
        baseMapper.reset();
    }
}
