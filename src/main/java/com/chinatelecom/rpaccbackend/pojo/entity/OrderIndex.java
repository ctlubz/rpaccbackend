package com.chinatelecom.rpaccbackend.pojo.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrderIndex {
    private Long orderId;
    private Date initTime;
    private String customerNumber;
    private Integer orderStatus;
    private String remark;
}
