package com.xn.bpmworkerevents.service;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xn.bpmworkerevents.domain.PoolWorker;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zly123
 * @since 2019-05-20
 */
public interface PoolWorkerService extends IService<PoolWorker> {

    @Override
    List<PoolWorker> selectList (Wrapper<PoolWorker> wrapper);

    List findAll();
}
