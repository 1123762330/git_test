package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Blocks;

public interface BlockService {
    double getAvgBlockReward(int limit);

    Blocks getLast();
}
