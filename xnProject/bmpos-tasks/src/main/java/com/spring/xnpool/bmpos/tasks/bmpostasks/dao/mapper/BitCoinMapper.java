package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper;



import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.BitCoin;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BitCoinMapper extends Mapper<BitCoin> {
     List<BitCoin> getBitCoinsByDebugObject(String debug_object);
}