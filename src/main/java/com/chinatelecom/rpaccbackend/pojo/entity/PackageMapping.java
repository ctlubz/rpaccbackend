package com.chinatelecom.rpaccbackend.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("tb_package_mapping")
public class PackageMapping {
    @TableField("package_name")
    private String packageName;
    @TableField("package_shortname")
    private String packageShortName;
}
