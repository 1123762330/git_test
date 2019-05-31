package com.cn.kafkaconsumer.dao.mapper;

import com.cn.kafkaconsumer.dao.model.PoolWorker;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PoolWorkerMapper {

    void insertPoolWorker (List<PoolWorker> list);
}