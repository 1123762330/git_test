package com.spring.xnpool.bmpos.tasks.bmpostasks.service;


import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Settings;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.SettingsModel;
import java.util.List;
import java.util.Map;

public interface SettingService {
    public List<Settings> getSettingsInfo();

    public List<Settings> selectByMap(Map map);

    SettingsModel querSettings(String pps_reward_type);

    int insert(SettingsModel settings);

    void updateSettingValue(SettingsModel settings);
}
