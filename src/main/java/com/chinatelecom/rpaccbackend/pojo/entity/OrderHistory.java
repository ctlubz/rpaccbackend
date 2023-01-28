package com.chinatelecom.rpaccbackend.pojo.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_order_history")
public class OrderHistory {
    @TableId
    private Long orderId;
    @TableField("busi_type")
    private String buisType;
    @TableField(value = "remark", typeHandler = FastjsonTypeHandler.class)
    private JSON remark;
    @TableField("order_status")
    private Integer orderStatus;
    @TableField("acceptor_id")
    private Long acceptorId;
    @TableField("is_automatic")
    private Integer automatic;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}
