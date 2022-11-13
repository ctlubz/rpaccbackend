package com.chinatelecom.rpaccbackend;

import com.chinatelecom.rpaccbackend.common.pojo.BusiPropertyEnum;
import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.TestDAO;
import com.chinatelecom.rpaccbackend.pojo.TestDO;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class RpacCbackendApplicationTests {
    @Autowired
    private OrderInfoDAO orderInfoDAO;
    @Autowired
    private TestDAO testDAO;
    @Test
    void contextLoads() {
        String testString =
                "归属本地网 停机类型是停类型停机子类型是停子类型停机备注是备注哦";
        System.out.println(StringSplit.split(testString, BusiPropertyEnum.PROD_SHUTDOWN.getBusiProperty()));
    }
    @Test
    void jsonTest(){
        TestDO testDO = new TestDO();
        String testString =
                "归属本地网 停机类型：是停类型 停机子类型：是停子类型 停机备注：是备注哦";
        testDO.setUsername(StringSplit.split(testString, BusiPropertyEnum.PROD_SHUTDOWN.getBusiProperty()));
        System.out.println(testDAO.insert(testDO));
    }
}
