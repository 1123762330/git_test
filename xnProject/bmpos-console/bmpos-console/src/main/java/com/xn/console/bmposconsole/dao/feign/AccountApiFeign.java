package com.xn.console.bmposconsole.dao.feign;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Component
@FeignClient(name = "account-api")
public interface AccountApiFeign {

    @RequestMapping(value = "/account/getUsers", method = RequestMethod.GET)
    JSONObject getUsers();

    //添加账号 普通用户 管理员 超级管理员
    @RequestMapping(value = "/account/insertUser", method = RequestMethod.POST)
    JSONObject
    InsertUser(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
                          @RequestParam(value = "phone") String phone,@RequestParam(value = "email") String email,@RequestParam(value = "state") int state,
                           @RequestParam(value = "rolename") String rolename,
                          @RequestParam(value = "roleremark") String roleremark);

    //修改用户权限
    @RequestMapping(value = "/account/updateRole", method = RequestMethod.POST)
    JSONObject update(@RequestParam(value = "userid") int userid, @RequestParam(value = "rolename") String rolename,
                      @RequestParam(value = "roleremark") String roleremark);


}
