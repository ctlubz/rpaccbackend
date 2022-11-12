package com.chinatelecom.rpaccbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_order_pool")
public class OrderPool {
    @TableId
    public Long orderId;
    @TableField("order_status")
    public Integer orderStatus;
    @TableField("remark")
    public String remark;
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
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    public Date updateTime;
    @TableLogic
    @TableField("is_deleted")
    public Integer deleted;
}
