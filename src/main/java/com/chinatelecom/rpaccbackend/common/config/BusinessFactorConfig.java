package com.chinatelecom.rpaccbackend.common.config;

import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.dao.CommonDAO;
import com.chinatelecom.rpaccbackend.dao.PackageMappingDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.PackageMapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
public class BusinessFactorConfig {
    public static JSONObject businessFactorJson;
    public static HashMap<String, String> statusMap = new HashMap<String, String>();
    @PostConstruct
    private void businessFactorInit(){
//        String jsonString = JsonUtil.readJsonFile("src/main/resources/BusinessFactorConfig.json");
        String jsonString = "{\n" +
                "  \"套餐停机\": [\"业务号码\", \"停机类型\", \"停机原因\", \"产品类型\", \"联系人\", \"联系电话\"],\n" +
                "  \"套餐复机\": [\"业务号码\", \"产品类型\"],\n" +
                "  \"叠加包、促销订购\": [\"业务号码\", \"订购/注销促销、叠加包名称\", \"终端串码\", \"特殊备注\"],\n" +
                "  \"补卡\": [\"业务号码\", \"产品类型\", \"UIM卡号\", \"补换卡费优惠\", \"3G卡是否升级为4G卡\"]\n" +
                "}";
        if(!Objects.isNull(jsonString)) {
            businessFactorJson = (JSONObject) JSONObject.parse(jsonString);
//            System.out.println(businessFactorJson);
        }
        statusMap.put("3", "待执行");
        statusMap.put("4", "执行中");
        statusMap.put("201", "RPA运行时报错");
        statusMap.put("254", "已驳回");
        statusMap.put("255", "已竣工");
    }
    @PreDestroy
    public void destroy(){

    }
//    @Scheduled(cron = "0 0 0/4 * * *")
//    @Scheduled(cron = "0/15 * * * * *")
//    public void factoryScheduled(){
//        init();
//    }

}
