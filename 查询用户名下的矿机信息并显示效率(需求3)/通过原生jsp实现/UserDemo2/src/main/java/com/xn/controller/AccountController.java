package com.xn.controller;

import com.xn.domain.Accounts;
import com.xn.domain.Works;
import com.xn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 帐户web
 */
@Controller
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/findUserList")
    public String findUserList(Model model, String username) {
        List<Works> userList = null;
        long time = 0;
        // 调用service的方法
        //判断有没有这个用户
        //Accounts user = accountService.findUser(username);
        long satrt = new Date().getTime();
        //去查找传入的用户名下的相关参数
        userList = accountService.findUserList(username);
        long end = new Date().getTime();
        time = end - satrt;

        model.addAttribute("list", userList);
        model.addAttribute("time", time);
        System.out.println("耗时" + time + "毫秒");
        return "UserList";
    }
}
