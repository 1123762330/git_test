package com.xn.find_xn_user.controller;

import com.google.gson.Gson;
import com.xn.find_xn_user.domain.*;
import com.xn.find_xn_user.service.UserListService;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserLoginController {
    @Autowired
    private UserListService userListService;

    @RequestMapping(value = "/pool/users/id", method = RequestMethod.GET)
    public String loginUser() {
        return "login";
    }


    @RequestMapping(value = "/pool/users/id", method = RequestMethod.POST)
    @ResponseBody
    public String loginUserPost(String username, String password) {
        Gson gson = new Gson();
        SuccessResponse response = null;
        FailResponse failResponse = null;
        Accounts accounts = null;
        try {
            accounts = userListService.selectUser(username);
            Integer id = accounts.getId();
            String pass = accounts.getPass();
            if (pass.equals(password)) {
                response = new SuccessResponse(username, id, 201);
                String json = gson.toJson(response);
                return json;
            }
            if (username == null || !pass.equals(password) || username.equals("")) {
                failResponse = new FailResponse("用户名或者密码错误", 400);
                String json = gson.toJson(failResponse);
                return json;
            }

        } catch (Exception e) {
            failResponse = new FailResponse("用户名或者密码错误", 400);
            JSONObject json = JSONObject.fromObject(failResponse);
            String responseStr = json.toString();
            return responseStr;
        }
        return null;


    }


}

