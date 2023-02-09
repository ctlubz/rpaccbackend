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
     * 时间每天1点03分30s
     * */
    @Scheduled(cron = "30 3 1 * * ?")
    public void deleteOrderIgnore(){
        Date date= new Date();
        long dateValue = date.getTime();
        dateValue = dateValue - 2L * 86400000;  // 86400000 每天毫秒数
        commonDAO.deleteByDate(new Date(dateValue));
    }
    /**
     * 定时清理执行中
     * 时间每天1点03分30s
     * */
//    @Scheduled(cron = "0 * 8,9,10,11,12,13,14,15,16,17 * * ? ")
//    public void restoreExecuting(){
//        Date date= new Date();
//        long dateValue = date.getTime();
//        dateValue = dateValue - 120000;  // 120000 两分钟
//        commonDAO.updateExecuting(new Date(dateValue));
//    }
}
