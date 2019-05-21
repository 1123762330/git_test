package com.xn.bmpos.manager.bmposmanager.controller;

import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinSetting;
import com.xn.bmpos.manager.bmposmanager.service.CoinSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class BaceController {

    @Autowired
    private CoinSettingService coinSettingService;

    public CoinSetting getUnit(String coin) {
        CoinSetting setting = coinSettingService.selectCoin(coin);
        return setting;
    }

}
