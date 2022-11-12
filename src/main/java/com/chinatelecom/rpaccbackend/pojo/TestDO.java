package com.chinatelecom.rpaccbackend.pojo;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.FastjsonTypeHandler;
import lombok.Data;

@Data
@TableName("tb_test")
public class TestDO {
    @TableId(type = IdType.AUTO)
    private Long id;
    @TableField(value = "username", typeHandler = FastjsonTypeHandler.class)
    private JSON username;
}
