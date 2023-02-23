package com.chinatelecom.rpaccbackend;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chinatelecom.rpaccbackend.common.config.BusinessFactorConfig;
import com.chinatelecom.rpaccbackend.common.util.BusinessUtil;
import com.chinatelecom.rpaccbackend.common.util.JsonUtil;
import com.chinatelecom.rpaccbackend.dao.*;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIgnore;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderPool;
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
    private PackageMappingDAO packageMappingDAO;
    @Autowired
    private OrderPoolService orderPoolService;
    @Autowired
    private RPAService rpaService;
    @Autowired
    private OrderIgnoreDAO orderIgnoreDAO;
    @Autowired
    private CommonDAO commonDAO;
    @Autowired
    private OrderPoolDAO orderPoolDAO;
    private final String newStr = "业务备注：【其它业务内容补充】：{测试},【套餐新装】：{套餐名称：" +
            "19元大流量|选号：|装机地址：|终端资源：|促销、可选：0元5G流量|终端串码：8986031724919035584|费用说明及金额：免预存|联系人" +
            "：|特殊备注：选填：例卡使用人、预约装机时间、经办人、公共信息备注、合账信息、改自主融合关系等等|}";
    private final String packageStr = "业务备注：【其它业务内容补充】：{机器人测试},【叠加包、促销订购】" +
            "：{*业务号码：17729278392*订购/注销促销、叠加包名称 ：10流量包*终端串码：无*特殊备注：无}";
    private final String shutdownStr = "业务备注：【其它业务内容补充】：{测试},【套餐停机】：" +
            "{业务号码：15353715917|停机类型：预拆机停机/无需求|联系人：马萍},";
    private final String prodshutdownString = "业务备注：【其它业务内容补充】：{}," +
            "【套餐停机】：{*业务号码：17729278392*停机类型：预拆机停机*停机原因：资费原因*产品类型：移动电话*联系人：单位*联系电话：17729270779}";
    @Test
    void contextLoads() {}
    @Test
    void xmlTest() throws Exception {
        System.out.println(rpaService.getOrder(null, false));
    }
    @Test
    void jsonTest(){
        List<Long> orderIdList = testDAO.orderIdList();
        orderIdList.removeIf(s -> s.equals(10788389L));
        System.out.println(orderIdList);
    }
    @Test
    void splitTest() throws Exception {
        String testStr = "*业务号码：18109198279*产品类型：*UIM卡号：8986032224919047772*补换卡费优惠：VIP优惠*3G卡是否升级为4G卡:是";
        System.out.println(BusinessUtil.parseRemark(testStr, "补卡"));
    }
    @Test
    void readJsonTest(){
        Date now = new Date();
        long twoMins = now.getTime() - 1000 * 60;
        System.out.println(commonDAO.getUnread(new Date(twoMins)));
    }
}
