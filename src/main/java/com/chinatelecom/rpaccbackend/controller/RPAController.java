package com.chinatelecom.rpaccbackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.enums.Result;
import com.chinatelecom.rpaccbackend.common.handler.AddOrderException;
import com.chinatelecom.rpaccbackend.common.handler.BusinessException;
import com.chinatelecom.rpaccbackend.dao.OrderIgnoreDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIgnore;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderInfo;
import com.chinatelecom.rpaccbackend.pojo.vo.OrderInfoVO;
import com.chinatelecom.rpaccbackend.service.OrderInfoService;
import com.chinatelecom.rpaccbackend.service.RPAService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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
    public Result<Object> addOrder(@RequestBody OrderInfoVO orderInfoVO) throws Exception{
        //判断工单号是否为空
        try {
            if (Objects.isNull(orderInfoVO.getOrderId())) {
                throw new AddOrderException(orderInfoVO.getOrderId());
            }
            //检索工单是否已经存在
            OrderInfo orderInfo = orderInfoService.selectOneById(orderInfoVO.getOrderId());
            if (!Objects.isNull(orderInfo)) {
                throw new AddOrderException(orderInfoVO.getOrderId());
            }
            rpaService.addOrder(orderInfoVO);
            return Result.ok();
        }
        catch (Exception e){
            throw new AddOrderException(orderInfoVO.getOrderId());
        }
    }
    @GetMapping("get")
    @ApiOperation("获取可执行工单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "业务动作"),
            @ApiImplicitParam(name = "execute", value = "是否执行（测试中，请勿用此参数）")
    })
    public Result<JSONObject> getOrder(
            @RequestParam(name = "type", required = false) String busiType,
            @RequestParam(name = "execute", required = false) boolean execute) throws Exception {
        return Result.ok(rpaService.getOrder(busiType, execute));
    }
}
