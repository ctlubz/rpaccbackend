package com.chinatelecom.rpaccbackend.pojo.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.util.Date;

@Data
public class OrderInfoVO {
    private Long orderId;
    private String buisType;
    private String customerName;
    private String customerNumber;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date initTime;
    private String salespersonName;
    private String salespersonPhone;
    private String fees;
    private String remark;
    private String localNet;
}