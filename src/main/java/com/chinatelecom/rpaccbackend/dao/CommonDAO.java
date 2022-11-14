package com.chinatelecom.rpaccbackend.dao;

import com.chinatelecom.rpaccbackend.pojo.entity.OrderIndex;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CommonDAO {
    @Select("SELECT \n" +
            "\ttb_order_info.order_id as orderId,\n" +
            "\ttb_order_info.init_time as initTime,\n" +
            "\ttb_order_info.customer_number as customerNumber,\n" +
            "\ttb_order_pool.order_status as orderStatus,\n" +
            "\ttb_order_info.remark\n" +
            "\tFROM tb_order_info, tb_order_pool \n" +
            "\tWHERE tb_order_info.order_id = tb_order_pool.order_id;")
    List<OrderIndex> orderPoolIndex();
}
