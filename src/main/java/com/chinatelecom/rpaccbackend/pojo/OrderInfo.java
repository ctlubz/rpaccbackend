package com.chinatelecom.rpaccbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_order_info")
public class OrderInfo {
    @TableId
    public Long orderId;
    @TableField("busi_type")
    public String busiType;
    @TableField("customer_name")
    public String customerName;
    @TableField("customer_number")
    public String customerNumber;
    @TableField("init_time")
    public Date initTime;
    @TableField("salesperson_name")
    public String salespersonName;
    @TableField("salesperson_phone")
    public String salespersonPhone;
    @TableField("fees")
    public String fees;
    @TableField("remark")
    public String remark;
    @TableField("local_net")
    public String localNet;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    public Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    public Date updateTime;
    @TableLogic
    @TableField("is_deleted")
    public Integer deleted;
}
