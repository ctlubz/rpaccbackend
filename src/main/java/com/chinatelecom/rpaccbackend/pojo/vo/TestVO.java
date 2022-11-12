package com.chinatelecom.rpaccbackend.pojo.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class TestVO {
    private Long username;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date date;
    private String pwd;
}
