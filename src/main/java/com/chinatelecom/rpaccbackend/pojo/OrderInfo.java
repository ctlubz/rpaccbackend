package com.chinatelecom.rpaccbackend.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tb_order_info")
public class OrderInfo {
    @TableId
    @TableField("order_id")
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
    @TableField("create_time")
    public Date createTime;
    @TableField("update_time")
    public Date updateTime;
    @TableField("is_deleted")
    public Date deleted;
}
