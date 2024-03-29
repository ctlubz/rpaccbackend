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
    void insertIntoExecuting(@Param("order_id") Long orderId);
    String getFullNameBySimpleName(@Param("simple_name")String simpleName, @Param("type")String type);
    List<String> getFullNameListBySimpleName(@Param("simple_name") String simpleName, @Param("type") String type);
    void updateExecuting(@Param("time")Date time);
    void insertIntoUnread(@Param("order_id") Long orderId);
    void deleteUnread(@Param("orderId") Long orderId);
    Long getUnread(@Param("create_time") Date time);
}
