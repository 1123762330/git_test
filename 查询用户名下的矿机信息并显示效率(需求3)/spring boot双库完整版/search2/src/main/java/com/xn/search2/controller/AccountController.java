package com.xn.search2.controller;

import com.xn.search2.domain.Accounts;
import com.xn.search2.domain.StatsPoolDay;
import com.xn.search2.domain.WorkVO;
import com.xn.search2.service.UserListService;
import com.xn.search2.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 帐户web
 */
@RestController
public class AccountController {

    @Autowired
    private UserListService userListService;
    //查找所有的用户
    @RequestMapping("/findUsers")
    public JsonUtil<WorkVO> findUser() {
        return userListService.findUserList();
    }

    //统计矿池在线天数
    @RequestMapping("/findPool")
    public JsonUtil<WorkVO> findPool() {
        // 调用service的方法
        return userListService.findPool();
    }

    //统计矿池在线小时
    @RequestMapping("/findPoolHour")
    public JsonUtil<WorkVO> findPoolHour() {
        // 调用service的方法
        return userListService.findPoolHour();
    }

    //统计用户在线小时
    @RequestMapping("/findUsersHour")
    public JsonUtil<WorkVO> findUsersHour(int puid) {
        // 调用service的方法
        return userListService.findUsersHour(puid);
    }

    //统计用户在线天数
    @RequestMapping("/findUserDay")
    public JsonUtil<WorkVO> findUserDay(int puid) {
        // 调用service的方法
        return userListService.findUserDay(puid);
    }

    //查询矿机在线天数
    @RequestMapping("/findWorkersDay")
    public JsonUtil<WorkVO> findWorkersDay(int puid) {
        return userListService.findWorkersDay(puid);
    }

    //查询矿机在线小时
    @RequestMapping("/findWorkersHour")
    public JsonUtil<WorkVO> findWorkersHour(int puid) {
        return userListService.findWorkersHour(puid);
    }
}
