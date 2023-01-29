package com.chinatelecom.rpaccbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderPool;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderPoolDAO extends BaseMapper<OrderPool> {
    int insertByParam(@Param("order_id") String order_id, @Param("busi_type") String businessType, @Param("remark") String remark);
    OrderPool selectByStatus(@Param("order_status") String status, @Param("busi_type") String busiType);
}
