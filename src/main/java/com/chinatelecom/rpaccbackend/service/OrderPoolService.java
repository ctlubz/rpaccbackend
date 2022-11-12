package com.chinatelecom.rpaccbackend.service;

import com.chinatelecom.rpaccbackend.dao.OrderPoolDAO;
import com.chinatelecom.rpaccbackend.pojo.OrderPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderPoolService {
    @Autowired
    private OrderPoolDAO orderPoolDAO;

    public List<OrderPool> orderPoolIndex(){
        return orderPoolDAO.selectList(null);
    }
}
