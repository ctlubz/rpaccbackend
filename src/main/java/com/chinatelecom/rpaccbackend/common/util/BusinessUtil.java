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
            case "改附属功能":
                result = changeSubFunction(remark, factorArray);
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
//            System.out.println(s);
            if(s.isEmpty())
                continue;
            int middle = s.indexOf('：');    // 根据：分割
            if(middle <= 0)
                continue;
            String key = s.substring(0, middle);    // 取key
            key = key.replace(" ", ""); // 去掉key中空格
            if(!factorArray.contains(key)){ // 如果不是业务需要的元素直接下一步
                continue;
            }
            String value = s.substring(middle + 1); // 取值
            if(key.equals("业务号码")){
                value = value.replace(" ", "");
            }
            result.put(key, value);
        }
        return result;
    }
    public static JSONObject packageSubscribe(String remark, JSONArray factorArray){
        JSONObject result = new JSONObject();
        String[] factorList = remark.split("\\*");
        for(String s : factorList){
            if(s.isEmpty()) {
                continue;
            }
            int middle = s.indexOf('：');    // 根据：分割
            if(middle <= 0)
                continue;
            String key = s.substring(0, middle);    // 取key
            key = key.replace(" ", ""); // 去掉key中空格
            if(!factorArray.contains(key)){ // 如果不是业务需要的元素直接下一步
                continue;
            }
            String value = s.substring(middle + 1); // 取值
            if(value.isEmpty()){    // 值为空跳过
                continue;
            }
            if(key.equals("业务号码")){
                value = value.replace(" ", "");
            }
            if(key.equals("订购/注销促销、叠加包名称")){ // 如果是订购/注销促销、叠加包名称需要进一步划分
                value = value.replace("；", ";");    // 半角换全角
                String[] subPackageList = value.split(";");
                JSONObject listJSONObject = new JSONObject();
                int i = 1;
                for(String ss : subPackageList){
                    if(ss.isEmpty()){
                        continue;
                    }
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
                        action = "退订";
                    }
                    packageSimpleName = ss.substring(pos+1);
                    packageSimpleName = packageSimpleName.toUpperCase();    // 简称小写转大写
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
    public static JSONObject changeSubFunction(String remark, JSONArray factorArray){
        JSONObject result = new JSONObject();
        String[] factorList = remark.split("\\*");
        for(String s : factorList){
            if(s.isEmpty()) {
                continue;
            }
            int middle = s.indexOf('：');    // 根据：分割
            if(middle <= 0)
                continue;
            String key = s.substring(0, middle);    // 取key
            key = key.replace(" ", ""); // 去掉key中空格
            if(!factorArray.contains(key)){ // 如果不是业务需要的元素直接下一步
                continue;
            }
            String value = s.substring(middle + 1); // 取值
            if(value.isEmpty()){    // 值为空跳过
                continue;
            }
            if(key.equals("业务号码")){
                value = value.replace(" ", "");
            }
            if(key.equals("订购/注销的叠加包名称")){ // 如果是订购/注销促销、叠加包名称需要进一步划分
                value = value.replace("；", ";");    // 半角换全角
                String[] subPackageList = value.split(";");
                JSONObject listJSONObject = new JSONObject();
                int i = 1;
                for(String ss : subPackageList){
                    if(ss.isEmpty()){
                        continue;
                    }
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
                        action = "退订";
                    }
                    packageSimpleName = ss.substring(pos+1);
                    packageSimpleName = packageSimpleName.toUpperCase();    // 简称小写转大写
                    String packageFullName = businessUtil.commonDAO.getFullNameBySimpleName(packageSimpleName, 2);
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
                listJSONObject.put("数量", i-1);
                result.put("列表", listJSONObject);
            }
            else {
                result.put(key, value);
            }
        }
        return result;
    }
}
