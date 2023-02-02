package com.chinatelecom.rpaccbackend.pojo.vo;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
public class OrderInfoVO {
    @ApiModelProperty(value = "工单号", required = true)
    private Long orderId;
    private String busiType;
    private String customerName;
    private String customerNumber;
    @ApiModelProperty(value = "发起时间", required = true)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date initTime;
    private String salespersonName;
    private String salespersonPhone;
    @ApiModelProperty(value = "费用信息")
    private String fees;
    @ApiModelProperty(value = "备注", required = true)
    private String remark;
    @ApiModelProperty(value = "归属本地网")
    private String localNet;
}