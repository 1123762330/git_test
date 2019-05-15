package com.xn.bmpos.manager.bmposmanager.controller;

import com.google.gson.Gson;
import net.sf.json.JSONObject;
import com.xn.bmpos.manager.bmposmanager.domain.model.*;
import com.xn.bmpos.manager.bmposmanager.exception.ErrorCode;
import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@Controller
@ResponseBody
@RequestMapping(value = "api")
public class LoginController {


    @Autowired
    private AccountAPI acccount;

    //todo 用户登录
    @PostMapping("/login")
    private Resp login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
                       @RequestParam(value = "place") String place){
        JSONObject resultJson = acccount.getLogin(username, password, place);
        FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
        if ("200".equals(feignModel.getStatus())) {
            return Resp.success(feignModel.getData());
        }
        return Resp.failcheck();
    }


    /**
     * todo  校验openid 是否存在
     * 第三方登录后还需要输入密码 登录
     */
    @GetMapping("/social")
    private Resp social(@RequestParam(value = "type") String type, @RequestParam(value = "openId") String openId){
        JSONObject resultJson = acccount.social(type, openId);
        FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * todo 注册
     */
    @PostMapping("/regist")
    private Resp registUser(@RequestParam(value = "username") String username,
                            @RequestParam(value = "phone") String phone,
                            @RequestParam(value = "password") String password) {
        JSONObject resultJson = acccount.registUser(username, phone, password);
        FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }


    /**
     * todo 修改
     */
    @PostMapping("/updateUser")
    private Resp updaeUser(UpdateUserModel userModel) {
        if (StringUtils.isEmpty(userModel.getUsername())) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR, "用户名不允许为空");
        }

        JSONObject resultJson = null;

        //emial修改
        if (userModel.getEmail() != null) {
            //todo 传递给oauth
            resultJson = acccount.updateUserByEmail(userModel.getUsername(), userModel.getEmail());
        }

        //手机号修改
        if (userModel.getPhone() != null) {
            resultJson = acccount.updateUserPhone(userModel.getUsername(), userModel.getPhone());
        }

        //密码修改
        if (userModel.getPwd() != null) {
            resultJson = acccount.updateUserPassword(userModel.getUsername(), userModel.getPwd());
        }

        //邮寄地址修改
        if (userModel.getStreet() != null) {
            resultJson = acccount.updateUserStreet(userModel.getUsername(), userModel.getStreet());
        }

        FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
        return Resp.success(feignModel);
    }


    /**
     * todo token 验证
     */
    @GetMapping(value = "/tokenVerify")
    public Resp tokenVerify(String token) {
        JSONObject resultJson = acccount.tokenVerify(token);
        FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }


    /**
     * 用户中心api
     */

    @GetMapping(value = "findLogin")
    public Resp findLogin(String token) {
        JSONObject resultJson = acccount.findLogin(token);
        FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
        return Resp.success(feignModel);
    }

}

