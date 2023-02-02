package com.chinatelecom.rpaccbackend.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chinatelecom.rpaccbackend.common.config.BusinessFactorConfig;
import com.chinatelecom.rpaccbackend.common.enums.OrderStatusEnum;
import com.chinatelecom.rpaccbackend.common.util.BusinessUtil;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderInfo;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderPool;
import com.chinatelecom.rpaccbackend.pojo.vo.OrderInfoVO;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

@Service
public class RPAService {
    private final OrderPoolDAO orderPoolDAO;
    private final OrderInfoDAO orderInfoDAO;
    public RPAService(OrderPoolDAO orderPoolDAO, OrderInfoDAO orderInfoDAO) {
        this.orderPoolDAO = orderPoolDAO;
        this.orderInfoDAO = orderInfoDAO;
    }
    /**
     * RPA取可以返回给中台的工单
     * 取一个状态为200或者201的订单
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
    public void addOrder(OrderInfoVO orderInfoVO) throws Exception{

        // 1. 初始化工单池对象
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderInfoVO.getOrderId());
        orderInfo.setInitTime(orderInfoVO.getInitTime());
        if(!Objects.isNull(orderInfoVO.getCustomerNumber())) {  // 客户号码
            orderInfo.setCustomerNumber(orderInfoVO.getCustomerNumber());
        }
        if(!Objects.isNull(orderInfoVO.getLocalNet())){ // 本地网
            orderInfo.setLocalNet(orderInfoVO.getLocalNet());
        }
        if(!Objects.isNull(orderInfoVO.getFees())){   // 费用
            orderInfo.setFees(orderInfoVO.getFees());
        }
        orderInfo.setRemark(orderInfoVO.getRemark());
        orderInfoDAO.insert(orderInfo);
        // 2. 初始化工单池对象
        String businessType = StringSplit.lastContext(orderInfo.getRemark(), false);    // 业务动作
        // @TODO 对业务动作进行筛选
        String remark = StringSplit.lastContext(orderInfo.getRemark(), true);   // 备注
        if(Objects.isNull(remark)){
            remark = orderInfoVO.getRemark();
        }
        orderPoolDAO.insertByParam(orderInfoVO.getOrderId().toString(), businessType, remark);
    }
    /**
     * 机器人取工单接口
     * */
    public JSONObject getOrder(String busiType, boolean execute) throws Exception {
        // 对busiType输入进行校验，不存在则设置为空
        if(!BusinessFactorConfig.businessFactorJson.containsKey(busiType)){
            busiType = null;
        }
        // 从工单池中筛选出工单
        OrderPool orderPool = orderPoolDAO.selectByStatus(String.valueOf(OrderStatusEnum.WAITING.getCode()), busiType);
        if(Objects.isNull(orderPool)){  //没有工单
            return null;
        }
        OrderInfo orderInfo = orderInfoDAO.selectById(orderPool.getOrderId());
        JSONObject result = new JSONObject();
        result.put("工单号", orderPool.getOrderId());
        result.put("业务类型", orderPool.getBusiType());
        result.put("归属本地网", orderInfo.getLocalNet());
        JSONObject businessRemark = BusinessUtil.parseRemark(orderPool.getRemark(), orderPool.getBusiType());
        result.putAll(businessRemark);
        // @TODO 执行中订单状态，定时返回
        return result;
    }
}
