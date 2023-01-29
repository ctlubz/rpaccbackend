package com.chinatelecom.rpaccbackend.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.handler.BusinessException;
import com.chinatelecom.rpaccbackend.common.pojo.Result;
import com.chinatelecom.rpaccbackend.dao.OrderIgnoreDAO;
import com.chinatelecom.rpaccbackend.dao.TestDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.TestDO;
import com.chinatelecom.rpaccbackend.pojo.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/test")
@ApiIgnore
public class TestController {
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private OrderIgnoreDAO orderIgnoreDAO;
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
    @GetMapping("boolean")
    public Result<Object> testBoolean() throws Exception {
        if(true){
            throw new BusinessException("自定义异常测试");
        }
        return Result.ok();
    }
}
