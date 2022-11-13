package com.chinatelecom.rpaccbackend.service;

import com.alibaba.fastjson.JSON;
import com.chinatelecom.rpaccbackend.common.pojo.BusiPropertyEnum;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import com.chinatelecom.rpaccbackend.dao.OrderLogDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.OrderLog;
import com.chinatelecom.rpaccbackend.pojo.OrderPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderPoolService {
    @Autowired
    private OrderPoolDAO orderPoolDAO;
    @Autowired
    private OrderLogDAO orderLogDAO;

    public List<OrderPool> orderPoolIndex(){
        return orderPoolDAO.selectList(null);
    }
    public void insertOrderPool(Long orderId, String remark, String busiType){
        OrderPool orderPool = new OrderPool();
        orderPool.setOrderId(orderId);
        JSON remarkJSON = StringSplit.split(remark, BusiPropertyEnum.PROD_SHUTDOWN.getBusiProperty());
        orderPool.setRemark(remarkJSON);
//        return orderPoolDAO.insert(orderPool);
    }
    public void updateOrderStatus(Long orderId, Integer status, String message){
        // 1. 找出订单
        OrderPool orderPool = orderPoolDAO.selectById(orderId);
        //如果订单为空
        if(Objects.isNull(orderId)){
            return;
        }
        //2. 更新订单状态
        orderPool.setOrderStatus(status);
        orderPoolDAO.updateById(orderPool);
        //3. 向log库中记录数据
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderId);
        orderLog.setType(status);
        orderLog.setMessage(message);
        orderLogDAO.insert(orderLog);
    }

}
