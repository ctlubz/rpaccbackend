package com.chinatelecom.rpaccbackend.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chinatelecom.rpaccbackend.common.pojo.OrderStatusEnum;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderInfo;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderPool;
import com.chinatelecom.rpaccbackend.pojo.vo.OrderInfoVO;
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
    private final OrderInfoDAO orderInfoDAO;
    public RPAService(OrderPoolDAO orderPoolDAO, OrderInfoDAO orderInfoDAO) {
        this.orderPoolDAO = orderPoolDAO;
        this.orderInfoDAO = orderInfoDAO;
    }
    /**
     * RPA取可以返回给中台的工单
     * */
    public HashMap<String, Object> feedbackService(){
        HashMap<String, Object> result = new HashMap<>();
        LambdaQueryWrapper<OrderPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderPool::getOrderStatus, OrderStatusEnum.SUCCESS.getCode())
                .or()
                .eq(OrderPool::getOrderStatus, OrderStatusEnum.EXCEPTION.getCode())
                .last("limit 1");
        OrderPool orderPool = orderPoolDAO.selectOne(lambdaQueryWrapper);
        if(Objects.isNull(orderPool)){
            return null;
        }
        result.put("orderId", orderPool.getOrderId());
        result.put("orderStatus", orderPool.getOrderStatus());
        result.put("message", orderPool.getMessage());
        return result;
    }
    /**
     * RPA添加工单接口
     * */
    public void addOrder(OrderInfoVO orderInfoVO){
        // 1.
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderInfoVO.getOrderId());
        orderInfo.setInitTime(orderInfoVO.getInitTime());
        if(!Objects.isNull(orderInfoVO.getCustomerNumber())) {
            orderInfo.setCustomerNumber(orderInfoVO.getCustomerNumber());
        }
        orderInfo.setRemark(orderInfoVO.getRemark());
        orderInfoDAO.insert(orderInfo);
        String businessType = StringSplit.lastContext(orderInfo.getRemark(), false);
        String remark = StringSplit.lastContext(orderInfo.getRemark(), true);
        if(Objects.isNull(remark)){
            remark = orderInfoVO.getRemark();
        }
        OrderPool orderPool = new OrderPool(orderInfoVO.getOrderId(), businessType, remark);
        orderPoolDAO.insert(orderPool);
    }
}
