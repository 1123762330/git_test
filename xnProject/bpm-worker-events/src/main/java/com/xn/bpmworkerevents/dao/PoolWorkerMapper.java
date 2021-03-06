package com.xn.bpmworkerevents.dao;

import com.xn.bpmworkerevents.entity.PoolWorker;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface PoolWorkerMapper {

    void insertPoolWorker(List<PoolWorker> list);
}