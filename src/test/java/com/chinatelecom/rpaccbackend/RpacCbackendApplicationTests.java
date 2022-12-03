package com.chinatelecom.rpaccbackend;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.util.BusinessUtil;
import com.chinatelecom.rpaccbackend.common.util.JsonUtil;
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
    private final String newStr = "业务备注：【其它业务内容补充】：{测试},【套餐新装】：{套餐名称：" +
            "19元大流量|选号：|装机地址：|终端资源：|促销、可选：0元5G流量|终端串码：8986031724919035584|费用说明及金额：免预存|联系人" +
            "：|特殊备注：选填：例卡使用人、预约装机时间、经办人、公共信息备注、合账信息、改自主融合关系等等|}";
    private final String packageStr = "业务备注：【其它业务内容补充】：{测试用},【叠加包、促销订购】：" +
            "{业务号码：18161792627|订购/注销促销、叠加包名称 ：订购-202104语音包5元包500分钟国内语音、40元40G流量包24期优惠为0元||},";
    private final String shutdownStr = "业务备注：【其它业务内容补充】：{测试},【套餐停机】：" +
            "{业务号码：15353715917|停机类型：预拆机停机/无需求|联系人：马萍},";
    private final String splitByDivide = "{\n" +
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
        String testStr = "aaa";
//        System.out.println(packageStr);
        System.out.println(Objects.isNull(StringSplit.lastContext(testStr, true)));
        System.out.println(BusinessUtil.businessRemark(packageStr));
    }
    @Test
    void readJsonTest(){
        JSONObject jsonObject = (JSONObject) JSONObject.parse(JsonUtil.readJsonFile("src/main/resources/config/BusinessFactorConfig.json"));
        System.out.println(jsonObject.get("套餐新装"));
    }
}
