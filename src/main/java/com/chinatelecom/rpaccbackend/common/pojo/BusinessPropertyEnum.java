package com.chinatelecom.rpaccbackend.common.pojo;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
public enum BusinessPropertyEnum {
    PROD_SHUTDOWN("产品停机", Arrays.asList("停机类型", "停机子类型", "停机备注")),
//    PACKAGE_SHUTDOWN("套餐停机", ),
    ;

    private String busiType;
    private List<String> busiProperty;

    private BusinessPropertyEnum(String busiType, List<String> busiProperty){
        this.busiType = busiType;
        this.busiProperty = busiProperty;
    }
}
