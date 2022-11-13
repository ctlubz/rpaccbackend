package com.chinatelecom.rpaccbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatelecom.rpaccbackend.pojo.OrderLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderLogDAO extends BaseMapper<OrderLog> {
}
