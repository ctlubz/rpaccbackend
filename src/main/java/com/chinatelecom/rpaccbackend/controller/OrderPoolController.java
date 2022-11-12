package com.chinatelecom.rpaccbackend.controller;

import com.chinatelecom.rpaccbackend.pojo.OrderPool;
import com.chinatelecom.rpaccbackend.pojo.common.Result;
import com.chinatelecom.rpaccbackend.service.OrderPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/orderpool")
public class OrderPoolController {
    @Autowired
    private OrderPoolService orderPoolService;
    @GetMapping("index")
    public Result<List<OrderPool>> orderPoolIndex(){
        return Result.ok(orderPoolService.orderPoolIndex());
    }
}
