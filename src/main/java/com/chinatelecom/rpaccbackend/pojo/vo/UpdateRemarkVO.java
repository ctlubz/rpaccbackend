package com.chinatelecom.rpaccbackend.pojo.vo;

import lombok.Data;

@Data
public class UpdateRemarkVO {
    private Long orderId;
    private String remark;
    private String busiType;
}
