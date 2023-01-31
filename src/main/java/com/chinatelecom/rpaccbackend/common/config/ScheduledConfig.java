package com.chinatelecom.rpaccbackend.common.config;

import com.chinatelecom.rpaccbackend.dao.CommonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ScheduledConfig {
    @Autowired
    private CommonDAO commonDAO;
    /**
     * 定时清理tb_order_ignore
     * 时间每天0点
     * */
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteOrderIgnore(){
        Date date= new Date();
        long dateValue = date.getTime();
        dateValue = dateValue - 2L * 86400000;  // 86400000 每天毫秒数
        commonDAO.deleteByDate(new Date(dateValue));
    }
}
