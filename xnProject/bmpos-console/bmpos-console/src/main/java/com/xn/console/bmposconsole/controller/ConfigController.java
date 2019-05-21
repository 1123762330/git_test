package com.xn.console.bmposconsole.controller;

import com.google.gson.Gson;
import com.xn.console.bmposconsole.dao.model.Config;
import com.xn.console.bmposconsole.dao.model.FeignModel;
import com.xn.console.bmposconsole.tools.GetData;
import com.xn.console.bmposconsole.tools.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * setting配置API
 */
@CrossOrigin
@RestController
@RequestMapping("/config")
public class ConfigController {

    @Autowired
    private GetData getData;


    @PostMapping("/delete")
    public Resp drop(String coin,Integer id) {
        Map<String,String> map = new HashMap<>();
        map.put("id",id.toString());
        Object dataToObject = getData.postDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    @PostMapping("/put") //todo
    public Resp change(String coin, Config config) {
        Map<String,String> map = new HashMap<>();
        map.put("name",config.getName());
        map.put("value",config.getValue());
        String remark = config.getRemark();
        if(remark != null){
            map.put("remark",remark);
        }
        if(config.getId()!=null){
            map.put("id",config.getId().toString());
        }
        Object dataToObject = getData.postDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    @PostMapping("/get")
    public Resp getByConfigKey(String coin,String name) {
        Map<String,String> map = new HashMap<>();
        map.put("name",name);
        Object dataToObject = getData.postDataToObject(coin, map);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    @PostMapping("/get_all")
    public Resp getAll(String coin) {
        Object dataToObject = getData.postDataToObject(coin, null);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * 返回默认配置
     * @return
     */
    @PostMapping("/get_default_config")
    public Resp getDefaultConfig(String coin){
        Object dataToObject = getData.postDataToObject(coin, null);
        FeignModel feignModel = new Gson().fromJson(dataToObject.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

}
