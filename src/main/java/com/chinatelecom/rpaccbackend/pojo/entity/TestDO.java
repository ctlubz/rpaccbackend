package com.chinatelecom.rpaccbackend.pojo.entity;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

@Data
@TableName("tb_test")
public class TestDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(value = "username", typeHandler = FastjsonTypeHandler.class)
    private JSON username;
    @TableLogic
    @TableField("is_deleted")
    private Integer deleted;
}
