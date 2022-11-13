package com.chinatelecom.rpaccbackend.pojo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_log")
public class OrderLog {
    @TableId
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
