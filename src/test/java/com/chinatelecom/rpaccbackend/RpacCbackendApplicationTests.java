package com.chinatelecom.rpaccbackend;

import com.chinatelecom.rpaccbackend.common.util.BusinessUtil;
import com.chinatelecom.rpaccbackend.dao.CommonDAO;
import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.dao.TestDAO;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    @Test
    void contextLoads() {

    }
    @Test
    void jsonTest(){
        String input = "13369159060 17782962560归属本地网 停机类型：违章停机 停机子类型：无 停机备注：无";
        Matcher matcher = StringSplit.numberPattern.matcher(input);
        if(matcher.find()){
            System.out.println(matcher.group());
        }
        input = matcher.replaceAll("");
        System.out.println(input);
    }
    @Test
    void splitTest(){
        String testStr = "业务备注：【其它业务内容补充】：{测试},【套餐停机】：" +
                "{业务号码：15353715917|停机类型：预拆机停机/无需求|联系人：马萍},";
        System.out.println(BusinessUtil.businessRemark(testStr));
    }
}
