package com.xn.console.bmposconsole.controller;


import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xn.console.bmposconsole.dao.feign.AccountApiFeign;
import com.xn.console.bmposconsole.dao.model.FeignModel;
import com.xn.console.bmposconsole.dao.model.UserModel;
import com.xn.console.bmposconsole.exception.ErrorCode;
import com.xn.console.bmposconsole.tools.Resp;
import org.apache.catalina.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用户管理
 */
@RequestMapping("api")
@RestController
public class UserController {


    @Autowired
    private AccountApiFeign accountApiFeign;

    /**
     * 查询所有用户
     * <p>
     * select t1.userid as userid, name as username,mobile as phone,email as email,  state as state, street as street,IP as IP,t1.createtime as createtime, t2.rolename as t_role,
     * t2.roleremark as roleremark FROM   t_user AS  t1   JOIN t_role AS t2 WHERE t1.roleid = t2.roleid
     */
    @GetMapping("/users")
    public Resp getUserList() {
        JSONObject resultJson = accountApiFeign.getUsers();
        FeignModel feignModel = null;
        if (resultJson != null) {
            feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
            return Resp.success(feignModel.getData());
        }
        return Resp.success(feignModel);
    }

    /**
     * 添加账号
     * <p>
     * 权限：超级管理员。。管理员
     */
    @PostMapping("/insertUser")
    public Resp insertUser(UserModel user) {
        //todo 添加一个用户需要 给权限  ---t_role(NONE,ADMIN) + roleremark(描述)
        //将数据发送给account
        JSONObject resultJson = accountApiFeign.InsertUser(user.getUsername(), user.getPassword(), user.getPhone(),
                user.getEmail(), user.getState(), user.getRolename(), roleName(user.getRolename()));
        //返回
        if (resultJson != null && "200".equals(resultJson.getString("status"))) {
            FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
            return Resp.success("添加成功",feignModel.getMessage());
        }
        return Resp.fail(ErrorCode.SYSTEM_ERROR, "添加失败", null);
    }

    public String roleName(String rolename) {
        String roleremark = null;
        switch (rolename) {
            case "ADMIN":
                roleremark = "管理员";
                break;
            case "NONE":
                roleremark = "普通用户";
                break;
            default:
                roleremark = "普通用户";
                break;
        }
        return roleremark;
    }

    /**
     *
     * 修改权限
     */
    @PostMapping("/updateRole")
    public Resp updateUser(UserModel userModel) {
        // userid，rolename 权限，权限不能为空，只能修改权限
        if (StringUtils.isEmpty(userModel.getRolename()) || userModel.getUserid() == 0) {
            return Resp.fail(ErrorCode.ISNotCanShu);
        }
        //发送给account
        JSONObject resultJson = accountApiFeign.update(userModel.getUserid(), userModel.getRolename(), roleName(userModel.getRolename()));
        if (resultJson != null) {
            FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
            return Resp.success(feignModel.getData());
        }
        return Resp.fail(ErrorCode.SYSTEM_ERROR, "用户权限修改失败", null);
    }

}
