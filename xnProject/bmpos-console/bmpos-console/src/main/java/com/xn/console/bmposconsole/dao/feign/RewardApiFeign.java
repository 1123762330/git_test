package com.xn.console.bmposconsole.dao.feign;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(name = "rewardCenter")
public interface RewardApiFeign {
    @RequestMapping(value = "/reward/insertReward", method = RequestMethod.POST)
    JSONObject InsertRewardSetting(@RequestParam(value = "coin") String coin, @RequestParam(value = "threshold") double threshold,
                                   @RequestParam(value = "rewardCount") int rewardCont, @RequestParam(value = "postUrl") String postUrl,
                                   @RequestParam(value = "enabled") String enabled);

    @RequestMapping(value = "/reward/updateReward", method = RequestMethod.POST)
    JSONObject updateReward(@RequestParam(value = "id")int id, @RequestParam(value = "coin") String coin,
                            @RequestParam(value = "threshold")double threshold,@RequestParam(value = "rewardCount") int rewardCount,
                            @RequestParam(value = "postUrl")String postUrl,@RequestParam(value = "enabled") String enabled);

    @RequestMapping(value = "/reward/values", method = RequestMethod.GET)
    JSONObject getReward();
}
