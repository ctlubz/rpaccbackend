package com.chinatelecom.rpaccbackend.controller;

import com.chinatelecom.rpaccbackend.pojo.entity.OrderInfo;
import com.chinatelecom.rpaccbackend.common.pojo.Result;
import com.chinatelecom.rpaccbackend.pojo.vo.OrderInfoVO;
import com.chinatelecom.rpaccbackend.pojo.vo.UpdateRemarkVO;
import com.chinatelecom.rpaccbackend.service.OrderInfoService;
import com.chinatelecom.rpaccbackend.service.OrderPoolService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/orderinfo")
@CrossOrigin
@Api(tags = "工单信息")
public class OrderInfoController {
    private final OrderInfoService orderInfoService;

    public OrderInfoController(OrderInfoService orderInfoService) {
        this.orderInfoService = orderInfoService;
    }

    @PutMapping("update")
    @ApiOperation("更新工单备注")
    public Result<Object> updateRemark(@RequestBody UpdateRemarkVO updateRemarkVO) throws Exception{
        orderInfoService.updateRemarkById(updateRemarkVO);
        return Result.ok().message("更新备注成功");
    }
}
