package com.chinatelecom.rpaccbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chinatelecom.rpaccbackend.common.pojo.OrderStatusEnum;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

@Service
public class RPAService {
    //Autowire
    private final OrderPoolDAO orderPoolDAO;
    public RPAService(OrderPoolDAO orderPoolDAO) {
        this.orderPoolDAO = orderPoolDAO;
    }
    /**
     * RPA取可以返回给中台的工单
     * */
    public LinkedHashMap<String, Object> feedbackService(){
        LinkedHashMap<String, Object> result = new LinkedHashMap<>();
        LambdaQueryWrapper<OrderPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderPool::getOrderStatus, OrderStatusEnum.SUCCESS.getCode()).or().eq(OrderPool::getOrderStatus, OrderStatusEnum.EXCEPTION.getCode()).last("limit 1");
        OrderPool orderPool = orderPoolDAO.selectOne(lambdaQueryWrapper);
        if(Objects.isNull(orderPool)){
            return null;
        }
        result.put("orderId", orderPool.getOrderId());
        result.put("orderStatus", orderPool.getOrderStatus());
        result.put("message", orderPool.getMessage());
        return result;
    }
}
