package com.cn.kafkaconsumer.dao.mapper;

import com.cn.kafkaconsumer.dao.model.SharesModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SharesMapper {

    void insertShares (List<SharesModel> list);



}