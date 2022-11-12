package com.chinatelecom.rpaccbackend.controller;

import com.chinatelecom.rpaccbackend.pojo.common.Result;
import com.chinatelecom.rpaccbackend.pojo.vo.TestVO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/test")
public class TestController {
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
}
