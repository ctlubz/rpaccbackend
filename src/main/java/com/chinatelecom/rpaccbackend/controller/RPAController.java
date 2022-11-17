package com.chinatelecom.rpaccbackend.controller;

import com.chinatelecom.rpaccbackend.common.pojo.Result;
import com.chinatelecom.rpaccbackend.dao.OrderIgnoreDAO;
import com.chinatelecom.rpaccbackend.pojo.entity.OrderIgnore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Objects;

@RestController
@RequestMapping("/api/rpa")
public class RPAController {
    @Autowired
    private OrderIgnoreDAO orderIgnoreDAO;
    @GetMapping("ignore")
    public Result<Integer> testBoolean(@RequestBody LinkedHashMap<String, Integer> requestBody) throws IOException {
        OrderIgnore orderIgnore = orderIgnoreDAO.selectById(requestBody.get("orderId"));
        return Result.ok(Objects.isNull(orderIgnore) ? 0 : 1);
    }
}
