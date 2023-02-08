package com.chinatelecom.rpaccbackend.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chinatelecom.rpaccbackend.pojo.entity.TestDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestDAO extends BaseMapper<TestDO> {
    List<Long> orderIdList();
}
