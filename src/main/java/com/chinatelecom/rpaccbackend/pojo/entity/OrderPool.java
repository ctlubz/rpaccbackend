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
    private Long orderId;
    @TableField("order_status")
    private Integer orderStatus;
    @TableField("busi_type")
    private String busiType;
    @TableField(value = "remark", typeHandler = FastjsonTypeHandler.class)
    private String remark;
    @TableField("user_id")
    private String userId;
    @TableField("lock_time")
    private Date lockTime;
    @TableField("robot_id")
    private Date robotId;
    @TableField("is_automatic")
    private Integer automatic;
    @TableField("have_sub_order")
    private Integer haveSubOrder;
    @TableField("message")
    private String message;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableLogic
    @TableField("is_deleted")
    private Integer deleted;
    public OrderPool(Long orderId, String busiType, String remark){
        this.orderId = orderId;
        this.busiType = busiType;
        this.remark = remark;
    }
    public OrderPool(){

    }
}
