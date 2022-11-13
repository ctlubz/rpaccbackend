package com.chinatelecom.rpaccbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_log")
public class OrderLog {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("order_id")
    public Long orderId;
    @TableField("sub_order_id")
    public Long subOrderId;
    @TableField("robot_id")
    public Integer robotId;
    @TableField("type")
    public Integer type;
    @TableField("message")
    public String message;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    public Date createTime;
    @TableField("is_deleted")
    public Integer deleted;
}
