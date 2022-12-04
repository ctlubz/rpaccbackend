package com.chinatelecom.rpaccbackend.common.handler;

import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException{
    private final String errorMessage;
    public BusinessException(String errorMessage){
        this.errorMessage = errorMessage;
    }
}
