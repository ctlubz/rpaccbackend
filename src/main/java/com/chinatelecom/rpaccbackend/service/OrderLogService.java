package com.chinatelecom.rpaccbackend.service;

import com.chinatelecom.rpaccbackend.dao.OrderLogDAO;
import com.chinatelecom.rpaccbackend.pojo.OrderLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderLogService {
    @Autowired
    private OrderLogDAO orderLogDAO;

    public boolean insertOrderLog(OrderLog orderLog){
        return orderLogDAO.insert(orderLog) != 0;
    }
}
