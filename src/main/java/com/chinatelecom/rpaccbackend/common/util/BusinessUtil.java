package com.chinatelecom.rpaccbackend.common.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.config.BusinessFactorConfig;
import com.chinatelecom.rpaccbackend.common.handler.BusinessException;
import com.chinatelecom.rpaccbackend.dao.CommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;

@Component
public class BusinessUtil {
    @Autowired
    private CommonDAO commonDAO;
    public static BusinessUtil businessUtil;
    @PostConstruct
    public void init(){
        businessUtil = this;
    }
//    public static JSONObject businessRemark(String inputStr){
//        // 业务类型
//        String businessType = StringSplit.lastContext(inputStr, false);
//        JSONObject businessFactorJson = (JSONObject) JSONObject.parse(JsonUtil.readJsonFile("src/main/resources/config/BusinessFactorConfig.json"));
//        if(Objects.isNull(businessType) || !businessFactorJson.containsKey(businessType)){   // 为空或者不支持
//             return null;
//        }
//        // 业务备注
//        String context = StringSplit.lastContext(inputStr, true);   // 为空
//        if(Objects.isNull(context)){
//            return null;
//        }
//        String[] contextList = context.split("\\|");
//        JSONObject result = new JSONObject();
//        JSONObject splitByDivideJson = (JSONObject) JSONObject.parse(JsonUtil.readJsonFile("src/main/resources/config/BusinessDivideConfig.json"));
//        JSONArray businessFactorList = businessFactorJson.getJSONArray(businessType);
//        for (String s : contextList){
//            int middle = s.indexOf('：');
//            String key = s.substring(0, middle);
//            // 去掉key中的空格
//            key = key.replace(" ", "");
//            if(!businessFactorList.contains(key)){
//                continue;
//            }
//            String value = s.substring(middle + 1);
//            //如果需要二次分割
//            if(splitByDivideJson.containsKey(key)){
//                JSONArray jsonArray = splitByDivideJson.getJSONArray(key);
//                String[] tempList = value.split("/");
//                // 分割元素和需求元素数量不相等
//                if(!Objects.equals(tempList.length, jsonArray.size())){
//                    return null;
//                }
//                for(int i = 0; i < tempList.length; i++){
//                    result.put(jsonArray.get(i).toString(), tempList[i]);
//                }
//            }
//            else{
//                result.put(key, value);
//            }
//        }
//        return result;
//    }

    public static JSONObject parseRemark(String remark, String businessType) throws Exception{
        JSONObject businessFactorJson = BusinessFactorConfig.businessFactorJson;
        // 业务做不了直接pass
        if(!businessFactorJson.containsKey(businessType)){
            return null;
        }
        JSONArray factorArray = businessFactorJson.getJSONArray(businessType);
        JSONObject result = null;
        switch (businessType){
            case "套餐停机":
            case "套餐复机":
            case "补卡":
                result = commonSplit(remark, factorArray);
                break;
            case "叠加包、促销订购":
                result = packageSubscribe(remark, factorArray);
                break;
            default:
                throw new BusinessException("未知业务动作");
        }
        return result;
    }
    public static JSONObject commonSplit(String remark, JSONArray factorArray){
        JSONObject result = new JSONObject();
        String[] factorList = remark.split("\\*");
        for(String s : factorList){
            if(s.isEmpty())
                continue;
            int middle = s.indexOf('：');    // 根据：分割
            String key = s.substring(0, middle);    // 取key
            key = key.replace(" ", ""); // 去掉key中空格
            if(!factorArray.contains(key)){ // 如果不是业务需要的元素直接下一步
                continue;
            }
            String value = s.substring(middle + 1); // 取值
            result.put(key, value);
        }
        return result;
    }
    public static JSONObject packageSubscribe(String remark, JSONArray factorArray){
        JSONObject result = new JSONObject();
        String[] factorList = remark.split("\\*");
        for(String s : factorList){
            if(s.isEmpty())
                continue;
            int middle = s.indexOf('：');    // 根据：分割
            String key = s.substring(0, middle);    // 取key
            key = key.replace(" ", ""); // 去掉key中空格
            if(!factorArray.contains(key)){ // 如果不是业务需要的元素直接下一步
                continue;
            }
            String value = s.substring(middle + 1); // 取值
            if(value.isEmpty()){    // 值为空跳过
                continue;
            }
            if(key.equals("订购/注销促销、叠加包名称")){ // 如果是订购/注销促销、叠加包名称需要进一步划分
                value = value.replace("；", ";");    // 半角换全角
                String[] subPackageList = value.split(";");
                JSONObject listJSONObject = new JSONObject();
                int i = 1;
                for(String ss : subPackageList){
                    JSONObject tempJSON = new JSONObject();
                    if(ss.length() < 4) // 没有业务动作或者叠加包名字直接返回
                        continue;
                    String action;  //  业务动作
                    String packageSimpleName;   // 叠加包简称
                    // 业务动作为1个字或2个字
                    int pos = ss.indexOf("-");  // 找到第一个'-'
                    if(pos > 2 || pos <= 0){
                        continue;
                    }
                    action = ss.substring(0, pos);
                    if(action.equals("加")){
                        action = "订购";
                    }
                    if(action.equals("取")){
                        action = "注销";
                    }
                    packageSimpleName = ss.substring(pos+1);
                    String packageFullName = businessUtil.commonDAO.getFullNameBySimpleName(packageSimpleName, 1);
                    if(!Objects.isNull(packageFullName)){   // 非空，是简称，需要替换为全名
                        tempJSON.put("名称", packageFullName);
                    }
                    else {
                        tempJSON.put("名称", packageSimpleName);
                    }
                    tempJSON.put("动作", action);
                    listJSONObject.put(Integer.toString(i), tempJSON);
                    i++;
                }
                listJSONObject.put("叠加包数量", i-1);
                result.put("叠加包列表", listJSONObject);
            }
            else {
                result.put(key, value);
            }
        }
        return result;
    }
}
