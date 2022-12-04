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
    @Autowired
    private OrderInfoService orderInfoService;
    @Autowired
    private OrderPoolService orderPoolService;
    @GetMapping("index")
    @ApiOperation("查询所有")
    public Result<List<OrderInfo>> orderInfoIndex(){
        return Result.ok(orderInfoService.orderInfoIndex());
    }
    @PutMapping("update")
    @ApiOperation("更新工单备注")
    public Result<Object> updateRemark(@RequestBody UpdateRemarkVO updateRemarkVO) throws Exception{
        orderInfoService.updateRemarkById(updateRemarkVO);
        return Result.ok().message("更新备注成功");
    }
    @GetMapping("prodshutdown")
    @ApiOperation("机器人产品停机取数")
    public Result<Object> shutdownInfo() throws Exception {
        return Result.ok(orderInfoService.shutdownInfo());
    }
    @PostMapping("add")
    @ApiOperation("添加订单信息接口")
    public Result<Object> orderInfoInsert(@RequestBody OrderInfoVO orderInfoVO){
        //判断工单号是否为空
        if (Objects.isNull(orderInfoVO.getOrderId())){
            return Result.fail().message("工单号不能为空");
        }
        //检索工单是否已经存在
        OrderInfo orderInfo = orderInfoService.selectOneById(orderInfoVO.getOrderId());
        if(!Objects.isNull(orderInfo)){
            return Result.fail().message("工单已存在");
        }
//        System.out.println(Objects.isNull(orderInfo));
//        System.out.println(orderInfoVO);
        //插入OrderInfo
        orderInfo = new OrderInfo();
        orderInfo.setOrderId(orderInfoVO.getOrderId());
        orderInfo.setInitTime(orderInfoVO.getInitTime());
        orderInfo.setCustomerNumber(orderInfoVO.getCustomerNumber());
        orderInfo.setRemark(orderInfoVO.getRemark());
        if(!Objects.isNull(orderInfoVO.getLocalNet())){
            orderInfo.setLocalNet(orderInfoVO.getLocalNet());
        }
        Integer result = orderInfoService.insertOrderInfo(orderInfo);
        if(result != 0){
            orderPoolService.insertOrderPool(orderInfo.getOrderId(), orderInfo.getRemark(), orderInfo.getBusiType());
            return Result.ok(result);
        }
        return Result.fail().message("请求失败");
    }
}
