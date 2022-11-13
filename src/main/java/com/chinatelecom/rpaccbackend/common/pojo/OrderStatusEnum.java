package com.chinatelecom.rpaccbackend.common.pojo;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    UNLOCKED(0,"未锁定"),
    UNCHECKED(1, "校验未提交"),
    UNCOMMITTED(2,"未提交"),
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
