package com.chinatelecom.rpaccbackend.service;

import com.chinatelecom.rpaccbackend.common.handler.BusinessException;
import com.chinatelecom.rpaccbackend.common.enums.OrderStatusEnum;
import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderInfo;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderPool;
import com.chinatelecom.rpaccbackend.pojo.vo.UpdateRemarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoDAO orderInfoDAO;
    @Autowired
    private OrderPoolDAO orderPoolDAO;

    public OrderInfo selectOneById(Long id){
        return orderInfoDAO.selectById(id);
    }
    public void updateRemarkById(UpdateRemarkVO updateRemarkVO){
        // 1. 根据id筛选出OrderPool订单
        OrderPool orderPool = orderPoolDAO.selectById(updateRemarkVO.getOrderId());
        if(Objects.isNull(orderPool)){
            throw new BusinessException("工单不存在");
        }
        if(!Objects.isNull(updateRemarkVO.getBusiType())){  // 如果不为空更新订单种类
            orderPool.setBusiType(updateRemarkVO.getBusiType());
        }
        // 2. 更新OrderPool的remark、order_status为等待执行
        orderPool.setRemark(updateRemarkVO.getRemark());
        orderPool.setOrderStatus(OrderStatusEnum.WAITING.getCode());
        orderPoolDAO.updateById(orderPool);
        // @TODO 4. 打给机器人
    }
}
