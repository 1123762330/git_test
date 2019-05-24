package com.xn.bpmworkerevents.dao;

import com.xn.bpmworkerevents.entity.Shares;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ShareModelMapper {

    void insertShares(List<Shares> list);
}
