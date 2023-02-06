package com.chinatelecom.rpaccbackend.pojo.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class OrderIndex {
    @ApiModelProperty(value = "工单号")
    private Long orderId;
    @ApiModelProperty(value = "发起时间")
    private Date initTime;
    @ApiModelProperty(value = "业务动作")
    private String busiType;
    @ApiModelProperty(value = "工单状态")
    private String orderStatus;
    @ApiModelProperty(value = "备注")
    private String remark;
}
