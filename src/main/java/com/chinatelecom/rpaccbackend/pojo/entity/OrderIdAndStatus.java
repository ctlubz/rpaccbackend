package com.chinatelecom.rpaccbackend.pojo.entity;

import lombok.Data;

@Data
public class OrderIdAndStatus {
    private Long orderId;
    private Integer orderStatus;
}
