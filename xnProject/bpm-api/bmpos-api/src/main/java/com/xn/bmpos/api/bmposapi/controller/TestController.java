package com.xn.bmpos.api.bmposapi.controller;

import com.xn.bmpos.api.bmposapi.feign.AccountAPI;
import com.xn.bmpos.api.bmposapi.feign.RewadAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @Autowired
    private AccountAPI accountAPI;
    @Autowired
    RewadAPI rewadAPI;
    @PostMapping("/test")
    public String test(){
        return accountAPI.selectUsersAndCoins().toString();
    }


    @RequestMapping(value = "/get_user", method = RequestMethod.GET)
    public String getUsernameByCoinAndAddress(@RequestParam("coin")String coin, @RequestParam("address") String address){
        return accountAPI.getUsernameByCoinAndAddress(coin,address).toString();
    }
    @RequestMapping(value = "/account", method = RequestMethod.GET)
    public String getReward(@RequestParam("address") String address){
        return rewadAPI.getReward(address).toString();
    }
}
