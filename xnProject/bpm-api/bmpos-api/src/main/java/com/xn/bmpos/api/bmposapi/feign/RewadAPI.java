package com.xn.bmpos.api.bmposapi.feign;

import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "rewardCenter")
public interface RewadAPI {
    @RequestMapping(value = "/reward/account", method = RequestMethod.GET)
    JSONObject getReward(@RequestParam("address")String address);

    @RequestMapping(value = "/reward/setting", method = RequestMethod.POST)
    JSONObject getRewardCenter(
            @RequestParam("coin") String coin,
            @RequestParam("accountName") String accountName,
            @RequestParam("address") String address,
            @RequestParam("balance") Double balance);
}
