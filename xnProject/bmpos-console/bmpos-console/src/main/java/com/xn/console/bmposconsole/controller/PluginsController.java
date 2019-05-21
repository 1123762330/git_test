package com.xn.console.bmposconsole.controller;


import com.google.gson.Gson;
import com.xn.console.bmposconsole.dao.mapper.CointypeMapper;
import com.xn.console.bmposconsole.dao.model.CoinType;
import com.xn.console.bmposconsole.dao.model.FeignModel;
import com.xn.console.bmposconsole.tools.GetData;
import com.xn.console.bmposconsole.tools.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/plugins")
public class PluginsController {
    @Autowired
    GetData getData;
    @Autowired
    CointypeMapper coinType;
    @GetMapping("/get")
    public Resp findByPluginsId(String coin,String pid){
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid);
        Object dataToObject = getData.getDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }
    @GetMapping("/get_error")
    public Resp getError(String coin,String pid){
        Map<String,String> map = new HashMap<>();
        map.put("pid",pid);
        Object dataToObject = getData.getDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    @GetMapping("/get_coin")
    public Resp getCoin(){
        List<String> coin = coinType.getCoin();
        return Resp.success(coin);
    }
}
