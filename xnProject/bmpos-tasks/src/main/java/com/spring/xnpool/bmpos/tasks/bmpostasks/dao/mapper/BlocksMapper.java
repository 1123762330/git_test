package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Blocks;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
@Component
public interface BlocksMapper extends Mapper<Blocks> {
    double getAvgBlockReward(int i);

    Blocks getLast();

    /**
     *从表中获取所有未经证实的块
     * @param confirmmations 确认int要求确认块的必要确
     * @return blocks列表
     */
    List<com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.payout.Blocks> getAllUnconfirmed(Integer confirmmations);
}