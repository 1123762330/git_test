package com.xn.bmpos.manager.bmposmanager.controller;

import com.google.gson.Gson;
import com.xn.bmpos.manager.bmposmanager.domain.model.FeignModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.UpdateUserModel;
import com.xn.bmpos.manager.bmposmanager.exception.ErrorCode;
import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import com.xn.bmpos.manager.bmposmanager.feign.SmsApi;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import com.xn.bmpos.manager.bmposmanager.util.Tools;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@ResponseBody
@RequestMapping(value = "api")
public class LoginController extends BaskController {


    @Autowired
    private AccountAPI acccount;

    @Autowired
    private SmsApi smsApi;

    //todo 用户登录
    @PostMapping("/login")
    private Resp login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
                       @RequestParam(value = "place") String place,
                       HttpServletRequest request) {

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Resp.fail(ErrorCode.ISNotCanShu);
        }

        JSONObject resultJson = acccount.getLogin(username, password, place);
        FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
        if ("200".equals(feignModel.getStatus())) {
            Object data = feignModel.getData();
            JSONObject jsonObject = JSONObject.fromObject(data);
            String usernameStr = jsonObject.getString("username");
            JSONObject userjson = acccount.finduserIdByUsersName(usernameStr);
            if (userjson.getInt("state") == 200) {
                Integer userId = userjson.getInt("data");
                HttpSession session = request.getSession();
                session.setAttribute("userId", userId);
            }
            return Resp.success(feignModel.getData());
        }
        return Resp.fail(ErrorCode.ISUserNot);
    }


    //todo 获取短信验证码
    @GetMapping("/sms")
    private Resp getSms(@RequestParam(value = "mobile") String mobile) {
        if (StringUtils.isEmpty(mobile)) {
            return Resp.fail(ErrorCode.ISNotCanShu);
        }
        JSONObject object = smsApi.getByAidAndCurrency(mobile, "60");
        if ("200".equals(object.getString("status"))) {
            return Resp.success(object.getString("message"));
        }
        return Resp.fail(object.getString("message"));
    }


    /**
     * todo  校验openid 是否存在
     * 第三方登录后还需要输入密码 登录
     */
    @GetMapping("/social")
    private Resp social(@RequestParam(value = "type") String type, @RequestParam(value = "openId") String openId) {

        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(openId)) {
            return Resp.fail(ErrorCode.ISNotCanShu);
        }

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
                            @RequestParam(value = "password") String password,
                            @RequestParam(value = "code") String code) {
        //todo 首先就去验证手机验证码
        if (!verfiByPhone(phone, code)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR, "验证码错误", null);
        }
        JSONObject resultJson = acccount.registUser(username, phone, password);
        FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }


    //手机号验证
    public boolean verfiByPhone(String phone, String code) {
        JSONObject object = smsApi.smsVerifi(phone, code);
        if (!"200".equals(object.getString("status"))) {
            return false;
        }
        return true;
    }

    //邮箱验证
    public boolean verfiByEmail(String email, String code) {
        //验证code
        JSONObject result = acccount.emailVerifi(email, code);
        if (!"200".equals(result.getString("status"))) {
            return false;
        }
        return true;
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

        if (userModel.getType() == 0) {
            if (!verfiByPhone(userModel.getPhone(), userModel.getCode())) {
                return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR, "短信验证码错误", null);
            }
        }

        if (userModel.getType() == 1) {
            if (!verfiByEmail(userModel.getEmail(), userModel.getCode())) {
                return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR, "邮箱验证码错误", null);
            }
        }

        //emial修改
        if (userModel.getEmail() != null && userModel.getCode() != null) {
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
    @PostMapping(value = "/findLogin")
    public Resp findLogin(String token) {
        JSONObject resultJson = acccount.findLogin(token);
        FeignModel feignModel = new Gson().fromJson(resultJson.toString(), FeignModel.class);
        return Resp.success(feignModel);
    }


    /**
     * 发送邮箱验证码
     *
     * @param email
     * @return
     */
    @PostMapping(value = "/sendEmail")
    public Resp sendEmail(@RequestParam(value = "email") String email) {
        if (StringUtils.isEmpty(email)) {
            return Resp.fail(ErrorCode.ISNotCanShu);
        }

        if (!Tools.isEmail(email)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR, "邮箱格式不正确", null);
        }

        JSONObject resultJson = acccount.sendEmail(email);
        if ("200".equals(resultJson.get("status"))) {
            return Resp.success(resultJson.get("message"));
        }
        return Resp.fail(resultJson.get("message"));
    }


}

