package com.xnpool.account.cotroller;

import com.xnpool.account.entity.EmailSetting;
import com.xnpool.account.entity.LoginHistroy;
import com.xnpool.account.entity.Role;
import com.xnpool.account.entity.User;
import com.xnpool.account.model.*;
import com.xnpool.account.service.ISaleAccountService;
import com.xnpool.account.service.exception.ErrorCode;
import com.xnpool.account.service.impl.EmailSettingService;
import com.xnpool.account.service.impl.LoginHistroyService;
import com.xnpool.account.service.impl.RoleService;
import com.xnpool.account.service.impl.UserService;
import com.xnpool.account.util.*;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@ResponseBody
@RequestMapping(value = "api")
public class LoginController extends BaskController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @Autowired
    ISaleAccountService accountService;
    @Autowired
    private LoginHistroyService loginHistroyService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private EmailSettingService emailSettingService;

    //todo 用户登录
    @PostMapping("/login")
    private Resp login(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
                       @RequestParam(value = "place") String place, HttpServletRequest request) throws UnsupportedEncodingException {
        Map<String, String> resultMap = new HashMap<>();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            return Resp.failcheck();
        }
        //todo 将数据传递给SSO登录
        HttpResultTokenModel httpResuletEntity = HttpTokenUtil.getToken(username, password);
        if (httpResuletEntity != null && !StringUtils.isEmpty(httpResuletEntity.getAccess_token())) {

            resultMap.put("token", httpResuletEntity.getAccess_token());
            resultMap.put("username", httpResuletEntity.getUser_nickname());
            resultMap.put("email", httpResuletEntity.getUserEmail());
            resultMap.put("phone", httpResuletEntity.getUserPhone());

            //todo 需要在manager 数据库中添加或者修改用户
            User user = userService.insertOrUpdateUser(httpResuletEntity, place, request);
            if (user != null) {
                Role role = roleService.getRole(user);
                if ("ADMIN".equals(role.getRolename())) {
                    resultMap.put("role", role.getRolename());
                }
            } else {
                resultMap.put("role", null);
            }

        } else {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR, "用户名或密码错误，请检查", resultMap);
        }
        return Resp.success("请求成功", resultMap);
    }


    /**
     * todo  校验openid 是否存在
     * 第三方登录后还需要输入密码 登录
     */
    @GetMapping("/social")
    private Resp social(@RequestParam(value = "type") String type, @RequestParam(value = "openId") String openId) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(type) || StringUtils.isEmpty(openId)) {
            return Resp.failcheck();
        }
        //todo  将数据传递给oauth 校验
        HttpResultJsonModel socailModel = HttpTokenUtil.getSocial(type, openId);

        if (socailModel.getState() == 1) {
            return Resp.success("已绑定该账号，输入密码登录", socailModel.getContent());
        } else {
            return Resp.fail(ErrorCode.SYSTEM_ERROR, socailModel.getAlertMessage(), null);
        }
    }

    /**
     * todo 注册
     */
    @PostMapping("/regist")
    private Resp registUser(@RequestParam(value = "username") String username,
                            @RequestParam(value = "phone") String phone,
                            @RequestParam(value = "password") String password,
                            HttpServletRequest request) {
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(phone) || StringUtils.isEmpty(password)) {
            return Resp.failcheck();
        }
        //todo 传递给oauth
        HttpResultJsonModel resultJsonModel = HttpTokenUtil.getSign(username, phone, password);

        if (resultJsonModel.getState() == 1) {
            //todo 注册成功 添加到本地数据库
            String loginIp = GetUserIPUtil.getIpAddr(request);
            userService.registUser(username, phone, password, loginIp);
            return Resp.success(resultJsonModel);
        }
        return Resp.fail(ErrorCode.SYSTEM_ERROR, resultJsonModel);
    }


    /**
     * todo 修改
     */
    @PostMapping("/updateUser")
    private Resp updaeUser(UpdateUserModel userModel) {
        if (StringUtils.isEmpty(userModel.getUsername())) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR, "用户名不允许为空");
        }
        HttpResultUpdateModel resultUpdateModel = null;
        //emial修改
        if (userModel.getEmail() != null) {
            //todo 传递给oauth
            resultUpdateModel = HttpTokenUtil.getUpdateUserEmail(userModel.getUsername(), userModel.getEmail());
            if (resultUpdateModel != null && resultUpdateModel.isSuccess()) {
                //todo 修改本库的数据
                userService.updateUserByEmail(userModel.getUsername(), userModel.getEmail());
                return Resp.success(userModel.getUsername() + "修改成功", null);
            }
        }
        //手机号修改
        if (userModel.getPhone() != null) {
            resultUpdateModel = HttpTokenUtil.getUpdateUserPhone(userModel.getUsername(), userModel.getPhone());
            if (resultUpdateModel != null && resultUpdateModel.isSuccess()) {
                //todo 修改本库的数据
                userService.updateUserPhone(userModel.getUsername(), userModel.getPhone());
                return Resp.success(userModel.getUsername() + "修改成功", null);
            }
        }
        //密码修改
        if (userModel.getPwd() != null) {
            resultUpdateModel = HttpTokenUtil.getUpdateUserPwd(userModel.getUsername(), userModel.getPwd());
            if (resultUpdateModel != null && resultUpdateModel.isSuccess()) {
                //todo 修改本库的数据
                userService.updateUserPassword(userModel.getUsername(), userModel.getPwd());
                return Resp.success(userModel.getUsername() + "修改成功", null);
            }
        }
        //邮寄地址修改
        if(userModel.getStreet() != null){
            if(userService.updateUserStreet(userModel.getUsername(),userModel.getStreet())>0){
                return Resp.success(userModel.getUsername() + "修改成功", null);
            }
        }
        return Resp.fail(ErrorCode.SYSTEM_ERROR, userModel.getUsername() + "修改失败", null);
    }


    /**
     * todo token 验证
     */
    @GetMapping(value = "tokenVerify")
    private Resp tokenVerify(String token) throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(token)) {
            return Resp.failcheck();
        }
        HttpResuletCheakTokenModel httpResuletCheakTokenModel = HttpTokenUtil.tokenVerify(token);
        if (httpResuletCheakTokenModel != null) {
            return Resp.success(httpResuletCheakTokenModel);
        }
        return Resp.fail(ErrorCode.UNLOGIN_ERROR, "token错误", httpResuletCheakTokenModel);
    }

    //todo 查询用户信息
    @GetMapping(value = "/findLogin")
    public Resp findLogin(@RequestParam(value = "token") String token) throws UnsupportedEncodingException {
        Resp resp = new Resp();
        Map<String,String> map = new HashMap<>();
        List<LoginHistroy> list = new ArrayList<LoginHistroy>();
        User user = new User();
        if (StringUtils.isEmpty(token)) {
            return Resp.failcheck();
        }
        //昵称
        HttpResuletCheakTokenModel httpResuletCheakTokenModel = HttpTokenUtil.tokenVerify(token);
        if(httpResuletCheakTokenModel.getUser_nickname() != null){
            user = userService.getUserByName(httpResuletCheakTokenModel.getUser_nickname());
        }
        //电话
        if(httpResuletCheakTokenModel.getUserPhone() != null){
            user = userService.getUserByPhone(httpResuletCheakTokenModel.getUserPhone());
        }
        //email
        if(httpResuletCheakTokenModel.getUserEmail() != null){
            user = userService.getUserByEmail(httpResuletCheakTokenModel.getUserEmail());
        }
        list = loginHistroyService.getLoginHsByUid(user.getUserid());
        map.put("username",user.getName());
        map.put("wx",user.getWxnick());
        map.put("qq",user.getQq());
        map.put("email",user.getEmail());
        map.put("phone",user.getMobile());
        map.put("address",user.getStreet());
        resp.setData(list);
        resp.setExt(map);
        resp.setStatus("200");
        resp.setMessage("请求成功");
        return resp;
    }

    @GetMapping("/sendEmail")
    @ResponseBody
    public Resp sendEmail(@RequestParam(value = "emailVerifi")String emailVerifi){
        //获取邮箱配置
        EmailSetting emailSetting = emailSettingService.findEmailSetting();
        //生成验证码
        String code = EmailUtil.createCode();
        //发送邮件
        EmailUtil.sendMail(emailVerifi, code, emailSetting);
        //存入redis
        boolean isTrue = redisUtil.hset(emailVerifi,emailVerifi, code, 300);
        //判断是否发送成功
       /* if(emailSetting == null || code == null || isTrue == false){
            return new Resp("500","发送邮件失败");
        }*/
        return new Resp("200","发送邮件成功");
    }

    @GetMapping("/emailVerifi")
    @ResponseBody
    public Resp emailVerifi(@RequestParam(value = "emailVerifi")String emailVerifi,@RequestParam(value = "code")String code){
        Object object = redisUtil.hget(emailVerifi,emailVerifi);
        if(code.equals(String.valueOf(object))){
            return new Resp("200","验证成功");
        }
        return new Resp("500","验证失败");
    }

}

