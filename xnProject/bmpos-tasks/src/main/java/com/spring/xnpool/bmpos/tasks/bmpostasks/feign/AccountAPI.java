package com.spring.xnpool.bmpos.tasks.bmpostasks.feign;

import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
@Component
@FeignClient(name = "account-api")
public interface AccountAPI {
    @RequestMapping(value = "/address/get_ac", method = RequestMethod.POST)
    JSONObject getByAidAndCurrency(@RequestParam("name")String name, @RequestParam("coin") String coin);

    @RequestMapping(value = "/mill/get_name", method = RequestMethod.GET)
    JSONObject getNameByOldName(@RequestParam("oldName")String oldName, @RequestParam("coin") String coin);

    @RequestMapping(value = "/account/get_user", method = RequestMethod.GET)
    JSONObject getUsernameByCoinAndAddress(@RequestParam("address")String address, @RequestParam("coin") String coin);


    @RequestMapping(value = "/account/findDataByAddress", method = RequestMethod.GET)
    JSONObject findDataByAddress(@RequestParam("coin")String coin, @RequestParam("address") String address);

}
