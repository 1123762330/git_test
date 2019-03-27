package com.xn.finduser.controller;

import com.xn.finduser.domain.Accounts;
import com.xn.finduser.domain.QueryResult;
import com.xn.finduser.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserList {
    @Autowired
    private UserListService userListService;

    @RequestMapping(value="/findUser",produces = "application/json;charset=UTF-8" )
    public List<Accounts> findUser() {
        System.out.println("执行到了");
        List<Accounts> userListApi = userListService.findUserListApi();
        return userListApi;


    }

    @RequestMapping("/find")
    public String find() {
        return "测试成功!";

    }


}
