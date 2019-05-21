package com.xn.bmpos.manager.bmposmanager.common;

import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinType;
import com.xn.bmpos.manager.bmposmanager.service.CoinTypeService;
import com.xn.bmpos.manager.bmposmanager.util.MonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * FileName:       PriceController
 * Author:         Administrator
 * Date:           2019/5/16 12:27
 * Description:
 */
@Component
public class PriceTask {

    @Autowired
    private CoinTypeService coinTypeService;

    @Scheduled(cron = "0 0/60 * * * ?")
    public void insertCoinPrice() {
        //获取数据库中币种和币价
        HashMap<String, String> allCoin = coinTypeService.findAllCoin();
        //获取网站接口中币种和币价
        Map<String, String> priceMap = MonUtils.getPrice();
        for (String coin : allCoin.keySet()) {
            String price = priceMap.get(coin);
            if (price == null) {
                coinTypeService.updateCoinPrice(coin, "1");
                continue;
            }
            coinTypeService.updateCoinPrice(coin, price);
        }
    }

}
