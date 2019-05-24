package com.xn.bpmworkerevents.service.impl;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.xn.bpmworkerevents.domain.PoolWorker;
import com.xn.bpmworkerevents.dao.PoolWorkerMapper;
import com.xn.bpmworkerevents.service.PoolWorkerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zly123
 * @since 2019-05-20
 */
@Service
public class PoolWorkerServiceImpl extends ServiceImpl<PoolWorkerMapper, PoolWorker> implements PoolWorkerService {
    @Autowired
    private PoolWorkerMapper poolWorkerMapper;

    @Override
    public List<PoolWorker> selectList (Wrapper<PoolWorker> wrapper) {
        return super.selectList(wrapper);
    }

    @Override
    public List findAll () {
        return poolWorkerMapper.findAll();
    }
}
