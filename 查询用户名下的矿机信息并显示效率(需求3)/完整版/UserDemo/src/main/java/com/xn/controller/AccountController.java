package com.xn.controller;

import com.xn.domain.Accounts;
import com.xn.domain.WorkVO;
import com.xn.util.JsonUtil;
import com.xn.domain.Works;
import com.xn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 帐户web
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    public long getTimeData(long time){
        return ((((time)*(1L<<32))/1024)/1024/1024);
    }

    //查找所有的用户
    @RequestMapping("/findUsers")
    public JsonUtil<List> findUser() {
        // 调用service的方法
        List<Accounts> userList = accountService.findUsers();
        return new JsonUtil<>(200, userList);
    }

    //查询所有的用户名下的矿机信息
    @RequestMapping("/findWorkerList")
    public JsonUtil<WorkVO> findWorkerList(int puid) {
        // 调用service的方法
        //程序开始时间
        long satrt = new Date().getTime();
        List<Works> workerList = accountService.findWorkerList(puid);
        //程序结束时间
        long end = new Date().getTime();
        //程序总耗时
        long time = end - satrt;
        //将数据封装在workVO里面,返回到前台页面上
        WorkVO workVO = new WorkVO(workerList, time);
        return new JsonUtil<>(200, workVO);
    }


}
