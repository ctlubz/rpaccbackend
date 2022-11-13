package com.chinatelecom.rpaccbackend.pojo.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

import java.util.Date;

@Data
@TableName(value = "tb_order_pool", autoResultMap = true)
public class OrderPool {
    @TableId
    public Long orderId;
    @TableField("order_status")
    public Integer orderStatus;
    @TableField(value = "remark", typeHandler = FastjsonTypeHandler.class)
    public JSON remark;
    @TableField("user_id")
    public String userId;
    @TableField("lock_time")
    public Date lockTime;
    @TableField("robot_id")
    public Date robotId;
    @TableField("is_automatic")
    public Integer automatic;
    @TableField("have_sub_order")
    public Integer haveSubOrder;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    public Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    public Date updateTime;
    @TableLogic
    @TableField("is_deleted")
    public Integer deleted;
}
