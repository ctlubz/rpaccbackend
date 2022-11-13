package com.chinatelecom.rpaccbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrderHistoryDAO extends BaseMapper<OrderHistory> {
}
