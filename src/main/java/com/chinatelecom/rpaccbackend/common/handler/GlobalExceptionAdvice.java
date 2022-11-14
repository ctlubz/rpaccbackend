package com.chinatelecom.rpaccbackend.common.handler;

import com.chinatelecom.rpaccbackend.common.pojo.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    //拦截所有异常信息
    @ExceptionHandler(Exception.class)
    public Result<Object> doException(Exception e){
        e.printStackTrace();
        return Result.fail().message("系统错误，请稍后重试！");
    }
}
