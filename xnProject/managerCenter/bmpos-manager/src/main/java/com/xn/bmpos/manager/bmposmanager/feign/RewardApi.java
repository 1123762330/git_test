package com.xn.bmpos.manager.bmposmanager.feign;

import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "rewardCenter")
public interface RewardApi {

    @RequestMapping(value = "/reward/account", method = RequestMethod.GET)
    JSONObject getByAidAndCurrency(@RequestParam("coin") String coin, @RequestParam("address") String address);
}
