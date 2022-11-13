package com.chinatelecom.rpaccbackend.service;

import com.alibaba.fastjson.JSON;
import com.chinatelecom.rpaccbackend.common.pojo.BusiPropertyEnum;
import com.chinatelecom.rpaccbackend.common.pojo.OrderStatusEnum;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.OrderInfo;
import com.chinatelecom.rpaccbackend.pojo.OrderPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoDAO orderInfoDAO;
    @Autowired
    private OrderPoolDAO orderPoolDAO;

    public List<OrderInfo> orderInfoIndex(){
        return orderInfoDAO.selectList(null);
    }

    public OrderInfo selectOneById(Long id){
        return orderInfoDAO.selectById(id);
    }
    public void updateRemarkById(Long id, String remark){
        //@TODO 事务回滚
        // 1. 根据id筛选出OrderInfo，OrderPool订单
        OrderInfo orderInfo = orderInfoDAO.selectById(id);
        if(Objects.isNull(orderInfo)){
            return;
        }
        OrderPool orderPool = orderPoolDAO.selectById(id);
        // 2. 更新OrderInfo订单备注
        orderInfo.setRemark(remark);
        // 3. 更新OrderPool的remark、order_status为等待执行
        JSON remarkJSON = StringSplit.split(remark, BusiPropertyEnum.PROD_SHUTDOWN.getBusiProperty());
        orderPool.setRemark(remarkJSON);
        orderPool.setOrderStatus(OrderStatusEnum.WAITING.getCode());
        orderInfoDAO.updateById(orderInfo);
        orderPoolDAO.updateById(orderPool);
        // @TODO 4. 打给机器人

    }
    public Integer insertOrderInfo(OrderInfo orderInfo){
        return orderInfoDAO.insert(orderInfo);
    }
}
