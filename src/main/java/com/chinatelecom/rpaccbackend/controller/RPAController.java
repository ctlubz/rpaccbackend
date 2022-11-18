package com.chinatelecom.rpaccbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.pojo.Result;
import com.chinatelecom.rpaccbackend.dao.OrderIgnoreDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIgnore;
import com.chinatelecom.rpaccbackend.service.RPAService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @PostMapping("ignore")
    @ApiOperation("无视工单查询")
    public Result<Integer> testBoolean(@RequestBody String body) throws Exception{
        JSONObject jsonObject = (JSONObject) JSONObject.parse(body);
        Long id = Long.parseLong((String) jsonObject.get("orderId"));
        OrderIgnore orderIgnore = orderIgnoreDAO.selectById(id);
        return Result.ok(Objects.isNull(orderIgnore) ? 0 : 1);
    }
    @GetMapping("feedback")
    @ApiOperation("反馈中台取数")
    public Result<Object> feedback(){
        return Result.ok(rpaService.feedbackService());
    }
}
