package com.spring.xnpool.bmpos.tasks.bmpostasks.controller;

import com.spring.xnpool.bmpos.tasks.bmpostasks.SettingAPI.Config;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IConfigService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/config")
public class ConfigController extends BaskController {

    @Autowired
    private IConfigService iConfigService;

    @PostMapping("/post")
    public ResponseResult<Void> create(Config config) {
        iConfigService.insert(config);
        return new ResponseResult<>(SUCCESS);
    }

    @PostMapping("/delete")
    public ResponseResult<Void> drop(Integer id) {
        iConfigService.delete(id);
        return new ResponseResult<>(SUCCESS);
    }

    @PostMapping("/put")
    public ResponseResult<Void> change(Config config) {
        iConfigService.updateByKey(config);
        return new ResponseResult<>(SUCCESS);
    }

    @PostMapping("/get")
    public ResponseResult<Config> getByConfigKey(String name) {
        Config data = iConfigService.findByKey(name);
        return new ResponseResult<>(SUCCESS, data);
    }

    @PostMapping("/get_all")
    public ResponseResult<List<Config>> getAll() {
        List<Config> data = iConfigService.findAll();
        return new ResponseResult<>(SUCCESS, data);
    }
}
