package com.chinatelecom.rpaccbackend.common.pojo;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    FINISH(200, "工单完成"),
    EXCEPTION(201, "工单异常")
    ;
    private final Integer code;
    private final String description;

    private OrderStatusEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }
}
