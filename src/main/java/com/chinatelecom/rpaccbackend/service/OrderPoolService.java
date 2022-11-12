package com.chinatelecom.rpaccbackend.service;

import com.alibaba.fastjson.JSON;
import com.chinatelecom.rpaccbackend.common.pojo.BusiProperty;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.OrderPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPoolService {
    @Autowired
    private OrderPoolDAO orderPoolDAO;

    public List<OrderPool> orderPoolIndex(){
        return orderPoolDAO.selectList(null);
    }
    public Integer insertOrderPool(Long orderId, String remark){
        // @TODO 备注转化
        OrderPool orderPool = new OrderPool();
        orderPool.setOrderId(orderId);
        JSON remarkJSON = StringSplit.split(remark, BusiProperty.PROD_SHUTDOWN.getBusiProperty());
        //orderPool.setRemark(StringSplit.split(remark, ));
        return orderPoolDAO.insert(orderPool);
    }
}
