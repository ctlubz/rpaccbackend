package com.chinatelecom.rpaccbackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.config.BusinessFactorConfig;
import com.chinatelecom.rpaccbackend.common.handler.BusinessException;
import com.chinatelecom.rpaccbackend.common.enums.Result;
import com.chinatelecom.rpaccbackend.dao.CommonDAO;
import com.chinatelecom.rpaccbackend.dao.OrderIgnoreDAO;
import com.chinatelecom.rpaccbackend.dao.TestDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIndex;
import com.chinatelecom.rpaccbackend.pojo.entity.TestDO;
import com.chinatelecom.rpaccbackend.service.RPAService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/test")
@ApiIgnore
public class TestController {
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private OrderIgnoreDAO orderIgnoreDAO;
    @Autowired
    private RPAService rpaService;
    @Autowired
    private CommonDAO commonDAO;
    @GetMapping("paramtest")
    public Result<Object> paramTest(@RequestParam(name = "type", required = false) boolean id){

        return Result.ok(id);
    }
    @PutMapping("update")
    public Result<Object> updateTest(){
        TestDO testDO = new TestDO();
        testDO.setId(4L);
        testDO.setUsername((JSON) JSON.parse("{'123':'45', '545':'asd'}"));
        testDAO.updateById(testDO);
        return Result.ok(testDO);
    }
    @GetMapping("get")
    @ApiOperation("获取可执行工单")
    public Result<JSONObject> getOrder(
            @RequestParam(name = "type", required = false) String busiType,
            @RequestParam(name = "execute", required = false) boolean execute
    ) throws Exception {
        return Result.ok(rpaService.getOrder(busiType, execute));
    }
    @GetMapping("index")
    public Result<List<OrderIndex>> orderPoolIndex(
            @RequestParam(name = "pageNum", required = false) Integer pageNum,
            @RequestParam(name = "pageSize", required = false) Integer pageSize
    ){
        // 页信息输入过滤
        if(Objects.isNull(pageNum)){
            pageNum = 1;
        }
        if(pageNum < 0){
            pageNum = 1;
        }
        if(Objects.isNull(pageSize)){
            pageSize = 100;
        }
        // 页大小
        if(pageSize > 100 || pageSize < 0){
            pageSize = 100;
        }
        PageHelper.startPage(pageNum, pageSize);
        // 工单号、发起时间、联系人电话、工单状态、备注
        List<OrderIndex> orderIndexList = commonDAO.orderPoolIndex();
        for (OrderIndex orderIndex : orderIndexList){
            orderIndex.setOrderStatus(BusinessFactorConfig.statusMap.get(orderIndex.getOrderStatus()));
        }
        PageInfo<OrderIndex> pageInfo = new PageInfo<>(orderIndexList);
//        System.out.println("总页数: " + pageInfo.getPages());
//        System.out.println("总记录数: " + pageInfo.getTotal());
//        System.out.println("当前页数: " + pageInfo.getPageNum());
//        System.out.println("当前页面记录数量: " + pageInfo.getSize());
        return Result.ok(pageInfo.getList());
//        return Result.ok(orderIndexList);
    }
}
