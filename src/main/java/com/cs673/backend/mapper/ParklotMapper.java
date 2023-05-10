package com.cs673.backend.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cs673.backend.entity.Parklot;


public interface ParklotMapper extends BaseMapper<Parklot> {
    //重置
    void reset();
}
