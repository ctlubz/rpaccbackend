package com.chinatelecom.rpaccbackend.service;

import com.alibaba.fastjson.JSON;
import com.chinatelecom.rpaccbackend.common.pojo.BusiPropertyEnum;
import com.chinatelecom.rpaccbackend.common.pojo.OrderStatusEnum;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import com.chinatelecom.rpaccbackend.dao.OrderHistoryDAO;
import com.chinatelecom.rpaccbackend.dao.OrderLogDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.OrderHistory;
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
    @Autowired
    private OrderHistoryDAO orderHistoryDAO;

    public List<OrderPool> orderPoolIndex(){
        return orderPoolDAO.selectList(null);
    }
    public void insertOrderPool(Long orderId, String remark, String busiType){
        OrderPool orderPool = new OrderPool();
        orderPool.setOrderId(orderId);
        JSON remarkJSON = StringSplit.split(remark, BusiPropertyEnum.PROD_SHUTDOWN.getBusiProperty());
        orderPool.setRemark(remarkJSON);
        orderPoolDAO.insert(orderPool);
    }
    public void updateOrderStatus(Long orderId, Integer status, String message){
        // @TODO 事务回滚
        // 1. 找出订单
        OrderPool orderPool = orderPoolDAO.selectById(orderId);
        //如果订单为空
        if(Objects.isNull(orderPool)){
            return;
        }
        orderPool.setOrderStatus(status);
        // 2. 订单状态是否为200，是200插入历史工单池
        if(Objects.equals(status, OrderStatusEnum.FINISH.getCode())){
            OrderHistory orderHistory= new OrderHistory();
            orderHistory.setOrderId(orderId);
            orderHistory.setRemark(orderPool.getRemark());
            orderHistory.setOrderStatus(OrderStatusEnum.FINISH.getCode());
            orderHistory.setAutomatic(orderPool.getAutomatic());
            //如果历史库插入成功，逻辑删除OrderPool的订单
            if(orderHistoryDAO.insert(orderHistory) != 0){
                orderPoolDAO.deleteById(orderPool.getOrderId());
            }
        }
        else{
            // 3. 非200状态
            // 3-1. 更新订单状态
            orderPoolDAO.updateById(orderPool);
            // 3-2. 向log库中记录数据
            OrderLog orderLog = new OrderLog();
            orderLog.setOrderId(orderId);
            orderLog.setType(status);
            orderLog.setMessage(message);
            orderLogDAO.insert(orderLog);
        }
    }

}
