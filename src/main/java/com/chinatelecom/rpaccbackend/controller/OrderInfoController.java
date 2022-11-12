package com.chinatelecom.rpaccbackend.controller;

import com.chinatelecom.rpaccbackend.pojo.OrderInfo;
import com.chinatelecom.rpaccbackend.pojo.common.Result;
import com.chinatelecom.rpaccbackend.service.OrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/orderinfo")
public class OrderInfoController {
    @Autowired
    private OrderInfoService orderInfoService;
    @GetMapping("index")
    public Result<List<OrderInfo>> orderInfoIndex(){
        return Result.ok(orderInfoService.orderInfoIndex());
    }
}
