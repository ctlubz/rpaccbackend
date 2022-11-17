package com.chinatelecom.rpaccbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_order_ignore")
public class OrderIgnore {
    @TableId
    private Long orderId;
    @JsonIgnore
    @TableField(value = "create_time", fill = FieldFill.INSERT, select = false)
    private Date createTime;
}
