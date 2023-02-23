package com.chinatelecom.rpaccbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

@Data
public class Unread {
    @TableId
    private Integer orderId;
    @TableField("create_time")
    private Date createTime;
}
