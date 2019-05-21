package com.xn.bmpos.manager.bmposmanager.service;

import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinSetting;
import com.xn.bmpos.manager.bmposmanager.domain.mapper.CoinSettingMapper;
import com.xn.bmpos.manager.bmposmanager.exception.ErrorCode;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CoinSettingService {
    @Autowired
    private CoinSettingMapper coinSettingMapper;

    /**
     * 查询t_coin_setting表中所有的数据
     */
    public List<CoinSetting> queryAllCoin() {
        List<CoinSetting> settings = coinSettingMapper.selectAllCoin();
        return settings;
    }

    public CoinSetting selectCoin (String coin) {
        return coinSettingMapper.selectCoin(coin);
    }

    public List<String> queryAllCoinType () {
        return coinSettingMapper.queryAllCoinType();
    }

}
