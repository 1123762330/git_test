package com.spring.xnpool.bmpos.tasks.bmpostasks.controller;

import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.AccountAPI;
import net.sf.json.JSON;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class TestController {
    @Autowired
    private AccountAPI accountAPI;

    @PostMapping("/test")
    public JSONObject test(@RequestParam("name")String name, @RequestParam("coin") String coin){
        return accountAPI.getByAidAndCurrency(name,coin);
    }
}
