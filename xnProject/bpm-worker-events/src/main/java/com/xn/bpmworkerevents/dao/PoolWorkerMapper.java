package com.xn.bpmworkerevents.dao;

import com.xn.bpmworkerevents.domain.PoolWorker;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.xn.bpmworkerevents.domain.Result;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zly123
 * @since 2019-05-20
 */

@Repository
@Mapper
public interface PoolWorkerMapper extends BaseMapper<PoolWorker> {
    List findAll ();

    void saveMessage (List<Result> list);
}
