package com.chinatelecom.rpaccbackend;

import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.TestDAO;
import com.chinatelecom.rpaccbackend.pojo.TestDO;
import com.chinatelecom.rpaccbackend.util.StringSplit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class RpacCbackendApplicationTests {
    @Autowired
    private OrderInfoDAO orderInfoDAO;
    @Autowired
    private TestDAO testDAO;
    @Test
    void contextLoads() {
        String testString =
                "归属本地网 停机类型：是停类型 停机子类型：是停子类型 停机备注：是备注哦";
        List<String> targetList = new ArrayList<>();
        targetList.add("停机类型");
        targetList.add("停机子类型");
        targetList.add("停机备注");
        System.out.println(StringSplit.split(testString, targetList));
    }
    @Test
    void jsonTest(){
        TestDO testDO = new TestDO();
        testDO.setUsername("5895");
        System.out.println(testDAO.insert(testDO));
    }
}
