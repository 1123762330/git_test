package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.Bitcoin;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Settings;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.SettingsMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.SettingsModel;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class SettingServiceImpl implements SettingService {
    @Autowired
    private SettingsMapper settingsMapper;

    @Autowired
    private BitCoinService bitCoinService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private ShareService shareService;

    @Autowired
    private TransacTionService transacTionService;

    @Autowired
    private Bitcoin bitcoin;

    @Override
    public List<Settings> getSettingsInfo() {
        List<Settings> settingsList = settingsMapper.selectAll();
        return settingsList;
    }

    @Override
    public List<Settings> selectByMap(Map map) {
        return settingsMapper.selectByExample(map);
    }

    public void setValue(String configKey, String configValue) {
        Settings settings = new Settings();
        settings.setConfigKey(configKey);
        settings.setConfigValue(configValue);
        settings.setConfigName("admin");
        settings.setCreateTime(new Date());
        settingsMapper.insert(settings);
    }

    @Override
    public SettingsModel querSettings(String pps_reward_type) {
        return settingsMapper.selectByName(pps_reward_type);
    }

    @Override
    public int insert(SettingsModel settings) {
        return settingsMapper.insertSettings(settings);
    }

    @Override
    public void updateSettingValue(SettingsModel settings) {
        settingsMapper.updateSettingValue(settings);
    }

}
