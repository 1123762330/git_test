package com.example.demo.controller;

import com.example.demo.domain.Accounts;
import com.example.demo.domain.WorkVO;
import com.example.demo.domain.Works;
import com.example.demo.service.UserListService;
import com.example.demo.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
    public JsonUtil<List> findUser() {
        // 调用service的方法
        List<Accounts> userList = userListService.findUserList();
        return new JsonUtil<>(200, userList);
    }

    //查询所有的用户名下的矿机信息
    @RequestMapping("/findWorkerList")
    public JsonUtil<WorkVO> findWorkerList(int puid) {
        return userListService.findWorkerList(puid);
    }
}
