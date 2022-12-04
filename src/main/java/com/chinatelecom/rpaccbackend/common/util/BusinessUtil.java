package com.chinatelecom.rpaccbackend.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.service.OrderPoolService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.*;

@Component
public class BusinessUtil {
    public static JSONObject businessRemark(String inputStr){
        // 业务类型
        String businessType = StringSplit.lastContext(inputStr, false);
        JSONObject businessFactorJson = (JSONObject) JSONObject.parse(JsonUtil.readJsonFile("src/main/resources/config/BusinessFactorConfig.json"));
        if(Objects.isNull(businessType) || !businessFactorJson.containsKey(businessType)){   // 为空或者不支持
             return null;
        }
        // 业务备注
        String context = StringSplit.lastContext(inputStr, true);   // 为空
        if(Objects.isNull(context)){
            return null;
        }
        String[] contextList = context.split("\\|");
        JSONObject result = new JSONObject();
        JSONObject splitByDivideJson = (JSONObject) JSONObject.parse(JsonUtil.readJsonFile("src/main/resources/config/BusinessDivideConfig.json"));
        JSONArray businessFactorList = businessFactorJson.getJSONArray(businessType);
        for (String s : contextList){
            int middle = s.indexOf('：');
            String key = s.substring(0, middle);
            // 去掉key中的空格
            key = key.replace(" ", "");
            if(!businessFactorList.contains(key)){
                continue;
            }
            String value = s.substring(middle + 1);
            //如果需要二次分割
            if(splitByDivideJson.containsKey(key)){
                JSONArray jsonArray = splitByDivideJson.getJSONArray(key);
                String[] tempList = value.split("/");
                // 分割元素和需求元素数量不相等
                if(!Objects.equals(tempList.length, jsonArray.size())){
                    return null;
                }
                for(int i = 0; i < tempList.length; i++){
                    result.put(jsonArray.get(i).toString(), tempList[i]);
                }
            }
            else{
                result.put(key, value);
            }
        }
        return result;
    }

    public static JSONObject parseRemark(String remark, String businessType) throws Exception{
        JSONObject businessFactorJson = (JSONObject) JSONObject.parse(JsonUtil.readJsonFile("src/main/resources/config/BusinessFactorConfig.json"));
        if(!businessFactorJson.containsKey(businessType)){
            return null;
        }
        JSONArray factorArray = businessFactorJson.getJSONArray(businessType);
        JSONObject result = null;
        switch (businessType){
            case "套餐停机":
                result = shutdownSplit(remark, factorArray);
                break;
            default:
                throw new IOException();
        }
        return result;
    }
    public static JSONObject shutdownSplit(String remark, JSONArray factorArray){
        JSONObject result = new JSONObject();
        String[] factorList = remark.split("\\|");
        for(String s : factorList){
            int middle = s.indexOf('：');    // 根据：分割
            String key = s.substring(0, middle);    // 取key
            key = key.replace(" ", ""); // 去掉key中空格
            if(!factorArray.contains(key)){ // 如果不是业务需要的元素直接下一步
                continue;
            }
            String value = s.substring(middle + 1); // 取值
            if(key.equals("停机类型")){ // 如果是停机类型需要进一步划分
                String[] tempList = value.split("/");
                result.put("停机类型", tempList[0]);
                if(tempList.length == 2){
                    result.put("停机子类型", tempList[1]);
                }
            }
            else {
                result.put(key, value);
            }
        }
        return result;
    }

}
