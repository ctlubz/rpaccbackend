package com.chinatelecom.rpaccbackend.common.config;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
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
                "  \"套餐复机\": [\"业务号码\", \"产品类型\"]\n" +
                "}";
        if(!Objects.isNull(jsonString)) {
            businessFactorJson = (JSONObject) JSONObject.parse(jsonString);
//            System.out.println(businessFactorJson);
        }
        statusMap.put("3", "待执行");
        statusMap.put("254", "执行失败");
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
