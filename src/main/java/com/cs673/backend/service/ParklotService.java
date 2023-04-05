package com.cs673.backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cs673.backend.entity.Parklot;


public interface ParklotService extends IService<Parklot> {
    void reset();
}
