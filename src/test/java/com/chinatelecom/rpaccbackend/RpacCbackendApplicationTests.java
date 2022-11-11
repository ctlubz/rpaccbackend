package com.chinatelecom.rpaccbackend;

import com.chinatelecom.rpaccbackend.util.StringSplit;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class RpacCbackendApplicationTests {

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
        String str = "123456";
        System.out.println(str.substring(1, 2));
    }
}
