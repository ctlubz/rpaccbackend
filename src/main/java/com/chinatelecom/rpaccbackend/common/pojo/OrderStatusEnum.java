package com.chinatelecom.rpaccbackend.common.pojo;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {
    UNLOCKED(0,"未锁定"),
    UNCHECKED(1, "校验未提交"),
    UNCOMMITTED(2,"未提交"),
    WAITING(3, "等待执行"),
    EXECUTING(4, "执行中"),
    SUCCESS(200, "工单完成，等待回单"),
    EXCEPTION(201, "工单异常"),
    IGNORE(254, "无法机器人执行，添加Ignore"),
    HISTORY(255,"工单完全完成")
    ;
    private final Integer code;
    private final String description;

    private OrderStatusEnum(Integer code, String description){
        this.code = code;
        this.description = description;
    }
}
