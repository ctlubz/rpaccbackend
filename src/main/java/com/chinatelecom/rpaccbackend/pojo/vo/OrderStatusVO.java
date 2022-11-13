package com.chinatelecom.rpaccbackend.pojo.vo;

import lombok.Data;

@Data
public class OrderStatusVO {
    private Long orderId;
    private Integer status;
    private String message;
}
