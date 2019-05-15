package com.xn.bmpos.manager.bmposmanager.common;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.netflix.discovery.converters.Auto;
import com.xn.bmpos.manager.bmposmanager.domain.model.FeignModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.HttpResuletCheakTokenModel;
import com.xn.bmpos.manager.bmposmanager.exception.ErrorCode;
import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import com.xn.bmpos.manager.bmposmanager.service.SettingService;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * 登录拦截器
 *
 * @author soft01
 */


public class LoginInterceptor implements HandlerInterceptor {

    private final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);

    private AccountAPI accountAPI;

    public LoginInterceptor(AccountAPI accountAPI) {
        this.accountAPI = accountAPI;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        boolean flag = true;
        try {
            request.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            //拿到请求头里面的token
            //String token = request.getHeader("");
            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc19hcHAiOjAsInVzZXJfbmFtZSI6IjE4MzI3NjQ2NjcwIiwic2NvcGUiOlsiYWxsIl0sInVzZXJQaG9uZSI6IjE4MzI3NjQ2NjcwIiwidXNlcl9uaWNrbmFtZSI6Iuihl-i-ueW3t-aAgOS4reeahOS6uiIsInVzZXJFbWFpbCI6IjE4MzI3NjQ2NjcwQHFxLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfbm9uZSJdLCJqdGkiOiJhMDlkMGE1Zi1lNWU3LTQ0ZmEtYWJlYi01YWU2M2QzMGY1NzIiLCJjbGllbnRfaWQiOiJ4bnBvb2wtYXBwIn0.w-564sms17tlcNVbBOleLkqnotdvyVaW1iWmHcODRrM";

            //验证token,获取token的内容
         /*   JSONObject jsonObject = accountAPI.tokenVerify(token);
            FeignModel feignModel = new Gson().fromJson(jsonObject.toString(), FeignModel.class);
            //获取tokenjson字符串的data属性,并转成Json
            Object feignModelData = feignModel.getData();
            JSONObject data = JSONObject.fromObject(feignModelData);
            //获取角色权限authorities对应的属性,并只取集合里面的第一个元素
            JSONArray authorities = data.getJSONArray("authorities");
            Object authoritie = authorities.get(0);
            //判断,如果状态值是200,并且权限是普通用户权限,则可以访问
            if ("200".equals(feignModel.getStatus()) && "ROLE_none".equals(authoritie)) {
                logger.info("用户可以访问!!!");
                flag = true;
            } else {
                response.getWriter().write("您没有权限访问此目录!!!");
                return false;
            }*/
            return true;
        } catch (IOException e) {
            logger.info("拦截器出现异常.");
        }
        return flag;
    }


}
