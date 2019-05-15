package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Blocks;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.BlocksMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockServiceImpl implements BlockService {
    @Autowired
    BlocksMapper blocksMapper;

    /**
     * 获取平均值
     *
     * @param limit
     * @return
     */
    @Override
    public double getAvgBlockReward(int limit) {
        return blocksMapper.getAvgBlockReward(limit);
    }

    /**
     * 获取最后一个计算值
     *
     * @return
     */
    @Override
    public Blocks getLast() {
        Blocks blocks = blocksMapper.getLast();
        return blocks;
    }

}
