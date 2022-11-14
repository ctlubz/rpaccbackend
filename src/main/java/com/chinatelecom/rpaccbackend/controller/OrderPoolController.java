package com.chinatelecom.rpaccbackend.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chinatelecom.rpaccbackend.dao.CommonDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIndex;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderPool;
import com.chinatelecom.rpaccbackend.common.pojo.Result;
import com.chinatelecom.rpaccbackend.pojo.vo.OrderStatusVO;
import com.chinatelecom.rpaccbackend.service.OrderPoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderpool")
@CrossOrigin
@Api(tags = "工单池")
public class OrderPoolController {
    @Autowired
    private OrderPoolService orderPoolService;
    @Autowired
    private CommonDAO commonDAO;
    @ApiOperation("工单池查询")
    @GetMapping("index")
    public Result<List<OrderIndex>> orderPoolIndex(){
        // 工单号、发起时间、联系人电话、工单状态、备注
        return Result.ok(commonDAO.orderPoolIndex());
    }

    @PutMapping("status")
    public Result<Object> updateOrderStatus(@RequestBody OrderStatusVO orderStatusVO){
        orderPoolService.updateOrderStatus(orderStatusVO.getOrderId(), orderStatusVO.getStatus(), orderStatusVO.getMessage());
        return Result.ok();
    }
}
