package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.BitCoin;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Settings;

import java.util.List;

public interface BitCoinService {
    List<BitCoin> getBitCoinsByDebugObject(String debug_object);
}
