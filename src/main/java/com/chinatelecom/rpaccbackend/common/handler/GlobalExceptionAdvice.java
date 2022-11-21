package com.chinatelecom.rpaccbackend.common.handler;

import com.chinatelecom.rpaccbackend.common.pojo.Result;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    //拦截所有异常信息
    @ExceptionHandler(Exception.class)
    public Result<Object> doException(Exception e){
        e.printStackTrace();
        return Result.fail().message("系统错误，请稍后重试！");
    }

    @ExceptionHandler(IOException.class)
    public Result<Object> ioExceptionHandler(IOException e){
        e.printStackTrace();
        return Result.fail().message("数据异常，请更正数据");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Object> httpMessageNotReadableException(HttpMessageNotReadableException e){
        e.printStackTrace();
        return Result.fail().message("传入数据异常，请更正重试");
    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<Object> missingServletRequestParameterException(MissingServletRequestParameterException e){
        e.printStackTrace();
        return Result.fail().message("请求头缺失，请更正重试");
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Object> methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        e.printStackTrace();
        return Result.fail().message("请求头数据格式不准确，请更正重试");
    }
}
