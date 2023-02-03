package com.chinatelecom.rpaccbackend.common.handler;

import lombok.Getter;

@Getter
public class AddOrderException extends RuntimeException{
    private final long orderId;
    public AddOrderException(long orderId){
        this.orderId = orderId;
    }
}
