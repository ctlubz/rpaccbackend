package com.chinatelecom.rpaccbackend.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_test")
public class TestDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField("username")
    private String username;
}
