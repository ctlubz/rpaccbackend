package com.chinatelecom.rpaccbackend.service;

import com.chinatelecom.rpaccbackend.dao.OrderInfoDAO;
import com.chinatelecom.rpaccbackend.pojo.OrderInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderInfoService {
    @Autowired
    private OrderInfoDAO orderInfoDAO;

    public List<OrderInfo> orderInfoIndex(){
        return orderInfoDAO.selectList(null);
    }
}
