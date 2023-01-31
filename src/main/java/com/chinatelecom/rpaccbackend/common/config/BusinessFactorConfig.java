package com.chinatelecom.rpaccbackend.common.config;

import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.util.JsonUtil;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Objects;

@Component
public class BusinessFactorConfig {
    public static JSONObject businessFactorJson;
    @PostConstruct
    private void init(){
//        String jsonString = JsonUtil.readJsonFile("src/main/resources/BusinessFactorConfig.json");
        String jsonString = "{\n" +
                "  \"套餐停机\": [\"业务号码\", \"停机类型\", \"停机原因\", \"产品类型\", \"联系人\", \"联系电话\"],\n" +
                "  \"套餐复机\": [\"业务号码\", \"产品类型\"]\n" +
                "}";
        if(!Objects.isNull(jsonString)) {
            businessFactorJson = (JSONObject) JSONObject.parse(jsonString);
//            System.out.println(businessFactorJson);
        }
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
