package com.chinatelecom.rpaccbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_order_ignore")
public class OrderIgnore {
    @TableLogic
    private Long orderId;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
}
