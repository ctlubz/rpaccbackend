package com.chinatelecom.rpaccbackend;

import com.chinatelecom.rpaccbackend.common.pojo.BusiPropertyEnum;
import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.dao.TestDAO;
import com.chinatelecom.rpaccbackend.pojo.TestDO;
import com.chinatelecom.rpaccbackend.common.util.StringSplit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

@SpringBootTest
class RpacCbackendApplicationTests {
    @Autowired
    private OrderInfoDAO orderInfoDAO;
    @Autowired
    private TestDAO testDAO;
    @Test
    void contextLoads() {
        String testString =
                "归属本地网 停机类型：违章停机 停机子类型：无 停机备注：无";
        System.out.println(StringSplit.split(testString, BusiPropertyEnum.PROD_SHUTDOWN.getBusiProperty()));
    }
    @Test
    void jsonTest(){
        System.out.println(Objects.equals(200, 200));
    }
}
