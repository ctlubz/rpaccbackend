package com.chinatelecom.rpaccbackend.common.pojo;

import lombok.Getter;

@Getter
public enum LogOrderStatusEnum {
    FINISH(200, "工单完成"),
    EXCEPTION(201, "工单异常")
    ;
    private final Integer code;
    private final String description;

    private LogOrderStatusEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }
}
