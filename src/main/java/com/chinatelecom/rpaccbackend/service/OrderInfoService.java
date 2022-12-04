package com.chinatelecom.rpaccbackend.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chinatelecom.rpaccbackend.common.handler.BusinessException;
import com.chinatelecom.rpaccbackend.common.pojo.BusinessPropertyEnum;
import com.chinatelecom.rpaccbackend.common.pojo.OrderStatusEnum;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderInfo;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderPool;
import com.chinatelecom.rpaccbackend.pojo.vo.UpdateRemarkVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

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
    public Integer insertOrderInfo(OrderInfo orderInfo){
        return orderInfoDAO.insert(orderInfo);
    }
    public JSON shutdownInfo() throws Exception {
        // 1. 从OrderPool中选出状态为3的
        LambdaQueryWrapper<OrderPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderPool::getOrderStatus, OrderStatusEnum.WAITING.getCode()).last("limit 1");
        OrderPool orderPool = orderPoolDAO.selectOne(lambdaQueryWrapper);
        // 如果找不到
        if(Objects.isNull(orderPool)){
            return null;
        }
        // 2. 从OrderInfo中选出信息
        OrderInfo orderInfo = orderInfoDAO.selectById(orderPool.getOrderId());
        Matcher matcher = StringSplit.numberPattern.matcher(orderInfo.getRemark());
        // 3. 往数据中添加电话号码等
        String result = orderPool.getRemark();
        JSONObject jsonObject = JSONObject.parseObject(result);
        if(matcher.find()){
            jsonObject.put("业务号码", matcher.group());
        }
        else{
            throw new Exception("没有业务号码");
        }
        jsonObject.put("工单号", orderInfo.getOrderId());
        //如果本地网为空
        if(Objects.isNull(orderInfo.getLocalNet())){
            jsonObject.put("归属本地网", "铜川本地网");
        }
        else {
            jsonObject.put("归属本地网", orderInfo.getLocalNet());
        }
            jsonObject.put("业务类型", "套餐停机");
        // @TODO 4. 更改订单状态为执行中
        return jsonObject;
    }
}
