package com.chinatelecom.rpaccbackend.dao;

import com.chinatelecom.rpaccbackend.pojo.entity.OrderIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface CommonDAO {
    List<OrderIndex> orderPoolIndex();
    void deleteByDate(@Param("time")Date time);
}
