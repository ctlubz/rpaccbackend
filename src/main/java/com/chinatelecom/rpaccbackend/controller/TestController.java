package com.chinatelecom.rpaccbackend.controller;

import com.chinatelecom.rpaccbackend.pojo.common.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping
    public Result<Object> testController(){
        return Result.ok("Hello");
    }
}
