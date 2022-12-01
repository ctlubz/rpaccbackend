package com.chinatelecom.rpaccbackend.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.service.OrderPoolService;
import com.google.common.collect.Sets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BusinessUtil {
    private static final String splitByDivide = "{\"停机类型\": [\"停机类型\", \"停机子类型\"]}";
    private static final String businessFactor = "{\"套餐停机\": [\"业务号码\", \"停机类型\"]}";
    @Autowired
    private OrderPoolService orderPoolService;
    private static final Set<String> businessTypeList = Sets.newHashSet("套餐停机");
    public static JSONObject businessRemark(String inputStr){
        // 业务类型
        String businessType = StringSplit.lastContext(inputStr, false);
        if(Objects.isNull(businessType) || !businessTypeList.contains(businessType)){   // 为空或者不支持
            return null;
        }
        // 业务备注
        String context = StringSplit.lastContext(inputStr, true);   // 为空
        if(Objects.isNull(context)){
            return null;
        }
        String[] contextList = context.split("\\|");
        JSONObject result = new JSONObject();
        JSONObject splitByDivideJson = (JSONObject) JSONObject.parse(splitByDivide);
        JSONObject businessFactorJson = (JSONObject) JSONObject.parse(businessFactor);
        JSONArray businessFactorList = businessFactorJson.getJSONArray(businessType);
        for (String s : contextList){
            int middle = s.indexOf('：');
            String key = s.substring(0, middle);
            if(!businessFactorList.contains(key)){
                continue;
            }
            String value = s.substring(middle+1);
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
}
