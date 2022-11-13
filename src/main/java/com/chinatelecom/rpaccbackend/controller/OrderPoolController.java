package com.chinatelecom.rpaccbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chinatelecom.rpaccbackend.pojo.OrderPool;
import com.chinatelecom.rpaccbackend.common.pojo.Result;
import com.chinatelecom.rpaccbackend.pojo.vo.OrderStatusVO;
import com.chinatelecom.rpaccbackend.service.OrderPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderpool")
@CrossOrigin
public class OrderPoolController {
    @Autowired
    private OrderPoolService orderPoolService;
    @GetMapping("index")
    public Result<List<OrderPool>> orderPoolIndex(){
        LambdaQueryWrapper<OrderPool> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(OrderPool::getOrderStatus, 0);
        return Result.ok(orderPoolService.orderPoolIndex());
    }

    @PostMapping("status")
    public Result<Object> updateOrderStatus(@RequestBody OrderStatusVO orderStatusVO){
        orderPoolService.updateOrderStatus(orderStatusVO.getOrderId(), orderStatusVO.getStatus(), orderStatusVO.getMessage());
        return Result.ok();
    }
}
