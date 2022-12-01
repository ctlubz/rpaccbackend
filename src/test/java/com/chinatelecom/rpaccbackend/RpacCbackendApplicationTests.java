package com.chinatelecom.rpaccbackend;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.util.BusinessUtil;
import com.chinatelecom.rpaccbackend.dao.CommonDAO;
import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.dao.TestDAO;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;

@SpringBootTest
class RpacCbackendApplicationTests {
    @Autowired
    private OrderInfoDAO orderInfoDAO;
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private CommonDAO commonDAO;
    @Autowired
    private OrderPoolDAO orderPoolDAO;
    private String splitByDivide = "{\n" +
            "  \"停机类型\": [\"停机类型\", \"停机子类型\"]\n" +
            "}";
    @Test
    void contextLoads() {
        String str = "预拆机停机/无需求";
        String[] tempList = str.split("/");
        System.out.println(tempList.length);
    }
    @Test
    void jsonTest(){
        JSONObject splitByDivideJson = (JSONObject) JSONObject.parse(splitByDivide);
        JSONArray jsonArray = splitByDivideJson.getJSONArray("停机类型");
        String str = "预拆机停机/无需求";
        String[] tempList = str.split("/");
        System.out.println(Objects.equals(tempList.length, jsonArray.size()));
        JSONObject result = new JSONObject();
        for(int i = 0; i < jsonArray.size(); i++){
//            System.out.println(i + " : " + jsonArray.get(i));
            result.put(jsonArray.get(i).toString(), i);
        }
        System.out.println(result);
    }
    @Test
    void splitTest(){
        String testStr = "业务备注：【其它业务内容补充】：{测试},【套餐停机】：" +
                "{业务号码：15353715917|停机类型：预拆机停机/无需求|联系人：马萍},";
        String testStr2 = "业务备注：【其它业务内容补充】：{测试},【套餐停机】：" +
                "{业务号码：15353715917|停机类型：预拆机停机/无需求|联系人：马萍},";
        System.out.println(testStr);
        System.out.println(BusinessUtil.businessRemark(testStr));
    }
}
