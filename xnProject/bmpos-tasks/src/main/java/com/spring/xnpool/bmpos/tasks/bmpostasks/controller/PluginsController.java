package com.spring.xnpool.bmpos.tasks.bmpostasks.controller;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.StateVO;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IErrorService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IPluginsStateService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plugins")
public class PluginsController extends BaskController {
    @Autowired
    IPluginsStateService pluginsStateService;
    @Autowired
    IErrorService errorService;
    @GetMapping("/get")
    public ResponseResult<StateVO> findByPluginsId(String pid){
        StateVO data = pluginsStateService.getByPluginsId(pid);
        return new ResponseResult<>(SUCCESS,data);
    }
    @GetMapping("/get_error")
    public ResponseResult<List<Error>> getError(String pid){
        List<Error> data= errorService.getByError(pid);
        return new ResponseResult<>(SUCCESS,data);
    }
}
