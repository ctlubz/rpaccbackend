package com.chinatelecom.rpaccbackend.controller;

import com.alibaba.fastjson.JSON;
import com.chinatelecom.rpaccbackend.common.pojo.Result;
import com.chinatelecom.rpaccbackend.dao.TestDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.TestDO;
import com.chinatelecom.rpaccbackend.pojo.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Objects;

@RestController
@RequestMapping("/test")
@ApiIgnore
public class TestController {
    @Autowired
    private TestDAO testDAO;
    @GetMapping
    public Result<Object> testController(){
        return Result.ok("Hello");
    }
    @PostMapping("date")
    public Result<Object> testFormat(@RequestBody TestVO testVO){
        if(Objects.isNull(testVO.getPwd())){
            System.out.println("pwd is null");
        }
        return Result.ok(testVO);
    }
    @PutMapping("update")
    public Result<Object> updateTest(){
        TestDO testDO = new TestDO();
        testDO.setId(4L);
        testDO.setUsername((JSON) JSON.parse("{'123':'45', '545':'asd'}"));
        testDAO.updateById(testDO);
        return Result.ok(testDO);
    }
    @GetMapping("boolean")
    public Result<Object> testBoolean(@RequestBody LinkedHashMap<String, Integer> orderId) throws IOException {
        System.out.println(orderId.get("orderId"));
        return Result.ok(orderId);
    }
}
