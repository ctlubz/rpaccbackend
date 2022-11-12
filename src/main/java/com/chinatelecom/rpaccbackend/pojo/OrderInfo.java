package com.chinatelecom.rpaccbackend.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_order_info")
public class OrderInfo {
    @TableId
    public Long orderId;
    @TableField("buis_type")
    public String buisType;
    @TableField("customer_id")
    public String customerId;
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
    @TableField(value = "update_time", fill = FieldFill.UPDATE)
    public Date updateTime;
    @TableLogic
    @TableField("is_deleted")
    public Date deleted;
}
