package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.BitCoin;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.BitCoinMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.BitCoinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BitcoinClientImpl implements BitCoinService {
    @Autowired
    BitCoinMapper bitCoinMapper;

    /**
     * 根据key取值
     *
     * @param debug_object
     * @return
     */
    @Override
    public List<BitCoin> getBitCoinsByDebugObject(String debug_object) {
        return bitCoinMapper.getBitCoinsByDebugObject(debug_object);
    }

}
