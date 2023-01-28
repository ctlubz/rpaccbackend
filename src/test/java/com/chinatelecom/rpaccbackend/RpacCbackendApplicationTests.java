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
import com.chinatelecom.rpaccbackend.service.OrderPoolService;
import com.chinatelecom.rpaccbackend.service.RPAService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;

@SpringBootTest
class RpacCbackendApplicationTests {
    @Autowired
    private OrderInfoDAO orderInfoDAO;
    @Autowired
    private TestDAO testDAO;
    @Autowired
    private OrderPoolService orderPoolService;
    @Autowired
    private RPAService rpaService;
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
    private final String prodshutdownString = "业务备注：【其它业务内容补充】：{}," +
            "【套餐停机】：{*业务号码：17729278392*停机类型：预拆机停机*停机原因：资费原因*产品类型：移动电话*联系人：单位*联系电话：17729270779}";
    @Test
    void contextLoads() {

    }
    @Test
    void xmlTest() throws Exception {
        System.out.println(rpaService.getOrder());
    }
    @Test
    void jsonTest(){
        String jsonStr = "{\n" +
                "    \"业务类型\": \"套餐停机\",\n" +
                "    \"orderId\": 7176220,\n" +
                "    \"归属本地网\": \"西安本地网\",\n" +
                "    \"备注\": {\n" +
                "        \"业务号码\": \"15353715917\",\n" +
                "        \"停机类型\": \"预拆机停机\",\n" +
                "        \"停机子类型\": \"无需求\",\n" +
                "        \"test\":[1,2]\n" +
                "    }\n" +
                "}";
        JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonStr);
        JSONObject remarkObject = jsonObject.getJSONObject("备注");
        jsonObject.putAll(remarkObject);
        jsonObject.remove("备注");
        System.out.println(jsonObject);
//        System.out.println(remarkObject);
    }
    @Test
    void splitTest() throws Exception {
        String testStr = "*业务号码：17729278392*停机类型：预拆机停机*停机原因：资费原因*产品类型：移动电话*联系人：单位*联系电话：17729270779";
        System.out.println(BusinessUtil.parseRemark(testStr, "套餐停机"));
    }
    @Test
    void readJsonTest(){
        JSONObject jsonObject = (JSONObject) JSONObject.parse(JsonUtil.readJsonFile("src/main/resources/config/BusinessFactorConfig.json"));
        System.out.println(jsonObject.get("套餐新装"));
    }
    @Test
    void dateTest(){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        String format = simpleDateFormat.format(date);
        System.out.println(format);
    }
}
