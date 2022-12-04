package com.chinatelecom.rpaccbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.pojo.Result;
import com.chinatelecom.rpaccbackend.dao.OrderIgnoreDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIgnore;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderInfo;
import com.chinatelecom.rpaccbackend.pojo.vo.OrderInfoVO;
import com.chinatelecom.rpaccbackend.service.OrderInfoService;
import com.chinatelecom.rpaccbackend.service.RPAService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/rpa")
@Api(tags = "机器人接口")
public class RPAController {
    @Autowired
    private OrderIgnoreDAO orderIgnoreDAO;
    @Autowired
    private RPAService rpaService;
    @Autowired
    private OrderInfoService orderInfoService;
    @GetMapping("ignore")
    @ApiOperation("机器人无视工单查询")
    public Result<Integer> testBoolean(@RequestParam Long orderId) throws Exception{
        OrderIgnore orderIgnore = orderIgnoreDAO.selectById(orderId);
        return Result.ok(Objects.isNull(orderIgnore) ? 0 : 1);
    }
    @GetMapping("feedback")
    @ApiOperation("机器人反馈中台取数")
    public Result<Object> feedback(){
        return Result.ok(rpaService.feedbackService());
    }
    @PostMapping("add")
    @ApiOperation("添加工单")
    public Result<Object> addOrder(@RequestBody OrderInfoVO orderInfoVO){
        //判断工单号是否为空
        if (Objects.isNull(orderInfoVO.getOrderId())){
            return Result.fail().message("工单号不能为空");
        }
        //检索工单是否已经存在
        OrderInfo orderInfo = orderInfoService.selectOneById(orderInfoVO.getOrderId());
        if(!Objects.isNull(orderInfo)){
            return Result.fail().message("工单已存在");
        }
        rpaService.addOrder(orderInfoVO);
        return Result.ok();
    }
    @PostMapping("get")
    @ApiOperation("获取可执行工单")
    public Result<JSONObject> getOrder() throws Exception {
        return Result.ok(rpaService.getOrder());
    }
}
