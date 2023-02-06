package com.chinatelecom.rpaccbackend.common.handler;

import com.chinatelecom.rpaccbackend.common.enums.Result;
import com.chinatelecom.rpaccbackend.dao.OrderIgnoreDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionAdvice {
    @Autowired
    private OrderIgnoreDAO orderIgnoreDAO;
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
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e){
        return Result.fail().message("请求方法(Method)错误");
    }
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<Object> sqlIntegrityConstrainViolationException(SQLIntegrityConstraintViolationException e){
        return Result.fail().message("违反数据库唯一约束");
    }
    @ExceptionHandler(BusinessException.class)
    public Result<Object> businessException(BusinessException e){
        return Result.fail().message(e.getErrorMessage());
    }
    @ExceptionHandler(AddOrderException.class)
    public Result<Object> addOrderException(AddOrderException e){
        // @TODO 处理
        OrderIgnore orderIgnore = orderIgnoreDAO.selectById(e.getOrderId());
        System.out.println(orderIgnore);
        if(Objects.isNull(orderIgnore)){
            orderIgnore = new OrderIgnore();
            orderIgnore.setOrderId(e.getOrderId());
            orderIgnoreDAO.insert(orderIgnore);
        }
        return Result.fail().message("添加工单失败，该工单已不可领取");
    }
}
