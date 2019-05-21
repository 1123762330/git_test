package com.xn.console.bmposconsole.service;

import com.xn.console.bmposconsole.dao.mapper.CoinSettingMapper;
import com.xn.console.bmposconsole.dao.model.CoinSetting;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 后台管理新增新的币种和配置信息
     */
    public void addCoinToSetting (CoinSetting coinSetting) {
        Integer rows = coinSettingMapper.addCoinToSetting(coinSetting);
        if(rows != 1){
            log.info("添加新币种服务出现问题! ");
        }
    }

    /**
     *修改数据
     * @param coinSetting
     */
    public void updateCoinToSetting (CoinSetting coinSetting) {
        Integer rows = coinSettingMapper.updateCoinToSetting(coinSetting);
        if(rows != 1){
            log.info("修改数据出现问题! ");
        }
    }

    /**
     * 点击修改回显数据
     * @param Id
     * @return
     */
    public CoinSetting findCoinbyId (Integer Id) {
        return coinSettingMapper.findCoinbyId(Id);
    }


}
