package com.chinatelecom.rpaccbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderInfoDAO extends BaseMapper<OrderInfo> {
}
