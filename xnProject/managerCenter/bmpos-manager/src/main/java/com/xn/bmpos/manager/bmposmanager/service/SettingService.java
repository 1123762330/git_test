package com.xn.bmpos.manager.bmposmanager.service;

import com.xn.bmpos.manager.bmposmanager.domain.dao.Setting;
import com.xn.bmpos.manager.bmposmanager.domain.mapper.SettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SettingService {
    @Autowired
    private SettingMapper settingMapper;

    public List<Setting> queryAllCoin() {
        List<Setting> settings = settingMapper.selectAllCoin();
        return settings;
    }

    public Setting selectCoin (String coin) {
        return settingMapper.selectCoin(coin);
    }

    public List<String> queryAllCoinType () {
        return settingMapper.queryAllCoinType();
    }
}
