package com.chinatelecom.rpaccbackend.controller;

import com.chinatelecom.rpaccbackend.common.pojo.Result;
import com.chinatelecom.rpaccbackend.dao.OrderIgnoreDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIgnore;
import com.chinatelecom.rpaccbackend.service.RPAService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Objects;

@RestController
@RequestMapping("/api/rpa")
@Api(tags = "机器人接口")
public class RPAController {
    @Autowired
    private OrderIgnoreDAO orderIgnoreDAO;
    @Autowired
    private RPAService rpaService;
    @GetMapping("ignore")
    @ApiOperation("无视工单查询")
    public Result<Integer> testBoolean(@RequestBody LinkedHashMap<String, Integer> requestBody) throws IOException {
        OrderIgnore orderIgnore = orderIgnoreDAO.selectById(requestBody.get("orderId"));
        return Result.ok(Objects.isNull(orderIgnore) ? 0 : 1);
    }
    @GetMapping("feedback")
    @ApiOperation("反馈中台取数")
    public Result<Object> feedback(){
        return Result.ok(rpaService.feedbackService());
    }
}
