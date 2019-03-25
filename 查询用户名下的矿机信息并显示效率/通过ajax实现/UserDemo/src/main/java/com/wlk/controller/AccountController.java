package com.wlk.controller;

import com.wlk.domain.Accounts;
import com.wlk.domain.WorkVO;
import com.wlk.util.JsonUtil;
import com.wlk.domain.Works;
import com.wlk.service.AccountService;
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

    @RequestMapping("/findUserList")
    public JsonUtil<WorkVO> findUserList(String username) {
        // 调用service的方法
        //判断有没有这个用户,如果没有则给出弹框提示
        Accounts user = accountService.findUser(username);
        if (user == null) {
            return new JsonUtil(400, "查询的用户名不存在");
        } else {
            //程序开始时间
            long satrt = new Date().getTime();
            //去查找传入的用户名下的相关参数
            List<Works> userList = accountService.findUserList(username);
            //程序结束时间
            long end = new Date().getTime();
            //程序总耗时
            long time = end - satrt;
            //将数据封装在workVO里面,返回到前台页面上
            WorkVO workVO = new WorkVO(userList, time);
            return new JsonUtil<>(200, workVO);
        }
    }
}
