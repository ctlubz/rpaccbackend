package com.chinatelecom.rpaccbackend.service;

import com.chinatelecom.rpaccbackend.common.pojo.OrderStatusEnum;
import com.chinatelecom.rpaccbackend.dao.OrderHistoryDAO;
import com.chinatelecom.rpaccbackend.dao.OrderIgnoreDAO;
import com.chinatelecom.rpaccbackend.dao.OrderLogDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderHistory;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIgnore;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderLog;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
public class OrderPoolService {
    private final OrderPoolDAO orderPoolDAO;
    private final OrderLogDAO orderLogDAO;
    private final OrderHistoryDAO orderHistoryDAO;
    private final OrderIgnoreDAO orderIgnoreDAO;
    public OrderPoolService(OrderHistoryDAO orderHistoryDAO, OrderIgnoreDAO orderIgnoreDAO, OrderLogDAO orderLogDAO, OrderPoolDAO orderPoolDAO) {
        this.orderHistoryDAO = orderHistoryDAO;
        this.orderIgnoreDAO = orderIgnoreDAO;
        this.orderLogDAO = orderLogDAO;
        this.orderPoolDAO = orderPoolDAO;
    }

    public List<OrderPool> orderPoolIndex(){
        return orderPoolDAO.selectList(null);
    }
    public void insertOrderPool(Long orderId, String remark, String busiType){
        OrderPool orderPool = new OrderPool();
        orderPool.setOrderId(orderId);
        //JSON remarkJSON = StringSplit.split(remark, BusiPropertyEnum.PROD_SHUTDOWN.getBusiProperty());
        //orderPool.setRemark(remarkJSON);
        orderPoolDAO.insert(orderPool);
    }
    @Transactional
    public void updateOrderStatus(Long orderId, Integer status, String message) throws IOException {
        // 1. 找出订单
        OrderPool orderPool = orderPoolDAO.selectById(orderId);
        //如果订单为空
        if(Objects.isNull(orderPool)){
            return;
        }
        orderPool.setOrderStatus(status);
        if(Objects.isNull(message)){
            orderPool.setOrderStatus(status);
        }
        orderPoolDAO.updateById(orderPool);
        // 2. 订单状态是否为200，是200插入历史工单池
        OrderHistory orderHistory = new OrderHistory();
        OrderLog orderLog = new OrderLog();
        switch (status){
            case 200:   //SUCCESS   机器人执行成功等待回单
            case 201:   //EXCEPTION 机器人执行异常，等待修改数据
                // 1. 记录log 2. 更新状态和状态信息,更新OrderPool message
                orderLog.setOrderId(orderId);
                orderLog.setType(status);
                orderLog.setMessage(message);
                orderLogDAO.insert(orderLog);
                orderPool.setOrderStatus(status);
                orderPool.setMessage(message);
                orderPoolDAO.updateById(orderPool);
                break;
            case 254:   //IGNORE 订单无法完成，添加tb_order_ignore tb_order_history  删除订单
                orderHistory.setOrderId(orderId);
                orderHistory.setOrderStatus(status);
                orderHistory.setAutomatic(2);   //标记无法完成
                orderHistoryDAO.insert(orderHistory);
                //添加table ignore
                OrderIgnore orderIgnore = new OrderIgnore();
                orderIgnore.setOrderId(orderId);
                orderIgnoreDAO.insert(orderIgnore);
                orderPoolDAO.deleteById(orderId);
                break;
            case 255:   //HISTORY订单完全完成，添加tb_order_history 删除订单
                orderHistory.setOrderId(orderId);
                orderHistory.setOrderStatus(status);
                orderHistory.setAutomatic(orderPool.getAutomatic());   //标记是否自动完成
                orderHistoryDAO.insert(orderHistory);
                orderPoolDAO.deleteById(orderId);
                break;
            default:
                throw new IOException();
        }
    }

}
