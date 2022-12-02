package com.chinatelecom.rpaccbackend.dao;

import com.chinatelecom.rpaccbackend.pojo.entity.OrderIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonDAO {
    List<OrderIndex> orderPoolIndex();
}
