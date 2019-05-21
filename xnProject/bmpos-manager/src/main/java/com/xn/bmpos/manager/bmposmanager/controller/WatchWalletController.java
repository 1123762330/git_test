package com.xn.bmpos.manager.bmposmanager.controller;

import com.xn.bmpos.manager.bmposmanager.exception.ErrorCode;
import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import com.xn.bmpos.manager.bmposmanager.util.OkHttpUtils;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: zly
 * @Date: 2019/5/14 10:02
 */

@RestController
@RequestMapping("api")
@Slf4j
public class WatchWalletController {
    @Value("${okHttpClient.Url}")
    private String ip;

    @Value("${okHttpClient.api}")
    private String path;

    @Autowired
    private AccountAPI acccount;

    /**
     * 添加监控钱包
     * @param coin    币种
     * @param address 钱包地址
     * @param walletname    备注名
     * @param token    前台返回的token
     * @return
     */
    @GetMapping(value = "/subscribe")
    public Object subscribe (String coin, String address, String walletname,String token) {
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(coin)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        Object walletData =null;
        //token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc19hcHAiOjAsInVzZXJfbmFtZSI6IjE4MzI3NjQ2NjcwIiwic2NvcGUiOlsiYWxsIl0sInVzZXJQaG9uZSI6IjE4MzI3NjQ2NjcwIiwidXNlcl9uaWNrbmFtZSI6Iuihl-i-ueW3t-aAgOS4reeahOS6uiIsInVzZXJFbWFpbCI6IjE4MzI3NjQ2NjcwQHFxLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfbm9uZSJdLCJqdGkiOiJhMDlkMGE1Zi1lNWU3LTQ0ZmEtYWJlYi01YWU2M2QzMGY1NzIiLCJjbGllbnRfaWQiOiJ4bnBvb2wtYXBwIn0.w-564sms17tlcNVbBOleLkqnotdvyVaW1iWmHcODRrM";
        try {
            String username = getUsersName(token);

            Map<String, String> getMap = new HashMap<>();
            getMap.put("coin", coin);
            getMap.put("address", address);
            getMap.put("walletname", walletname);
            getMap.put("relationname", username);
            getMap.put("type", "web");

            String[] split = path.split(",");
            String hostUrl = ip + split[0];
            String result = OkHttpUtils.get(hostUrl, getMap);
            if(result==""||result==null){
                return new Resp("400","查询返回的数据为null");
            }
            walletData = JSONObject.fromObject(result).get("message");

        } catch (Exception e) {
            log.info("添加钱包失败.");
        }
        return Resp.success(walletData);
    }

    /**
     * 查询绑定钱包列表
     * @param token
     * @return
     */
    @GetMapping(value = "/selectWallet")
    public Resp selectWallet (String token) {
        Object walletData =null;
        //token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc19hcHAiOjAsInVzZXJfbmFtZSI6IjE4MzI3NjQ2NjcwIiwic2NvcGUiOlsiYWxsIl0sInVzZXJQaG9uZSI6IjE4MzI3NjQ2NjcwIiwidXNlcl9uaWNrbmFtZSI6Iuihl-i-ueW3t-aAgOS4reeahOS6uiIsInVzZXJFbWFpbCI6IjE4MzI3NjQ2NjcwQHFxLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfbm9uZSJdLCJqdGkiOiJhMDlkMGE1Zi1lNWU3LTQ0ZmEtYWJlYi01YWU2M2QzMGY1NzIiLCJjbGllbnRfaWQiOiJ4bnBvb2wtYXBwIn0.w-564sms17tlcNVbBOleLkqnotdvyVaW1iWmHcODRrM";
        try {
            String username = getUsersName(token);
            Map<String, String> getMap = new HashMap<>();
            getMap.put("relationname", username);
            getMap.put("type", "web");

            String[] split = path.split(",");
            String hostUrl = ip + split[1];
            String result = OkHttpUtils.get(hostUrl, getMap);
            if(result==""||result==null){
                return new Resp("400","查询返回的数据为null");
            }
            walletData = JSONObject.fromObject(result).get("data");
        } catch (Exception e) {
            log.info("查询钱包列表失败");
        }
        return Resp.success("查询成功",walletData);
    }

    /**
     * 删除钱包
     * @param token
     * @param walletId
     * @return
     */
    @GetMapping(value = "/deleteWallet")
    public Resp deleteWallet (String token,Integer walletId) {
        Object message =null;
        //token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc19hcHAiOjAsInVzZXJfbmFtZSI6IjE4MzI3NjQ2NjcwIiwic2NvcGUiOlsiYWxsIl0sInVzZXJQaG9uZSI6IjE4MzI3NjQ2NjcwIiwidXNlcl9uaWNrbmFtZSI6Iuihl-i-ueW3t-aAgOS4reeahOS6uiIsInVzZXJFbWFpbCI6IjE4MzI3NjQ2NjcwQHFxLmNvbSIsImF1dGhvcml0aWVzIjpbIlJPTEVfbm9uZSJdLCJqdGkiOiJhMDlkMGE1Zi1lNWU3LTQ0ZmEtYWJlYi01YWU2M2QzMGY1NzIiLCJjbGllbnRfaWQiOiJ4bnBvb2wtYXBwIn0.w-564sms17tlcNVbBOleLkqnotdvyVaW1iWmHcODRrM";
        try {
            String username = getUsersName(token);
            Map<String, String> getMap = new HashMap<>();
            getMap.put("walletid", walletId.toString());
            getMap.put("relationname", username);

            String[] split = path.split(",");
            String hostUrl = ip + split[2];
            String result = OkHttpUtils.get(hostUrl, getMap);
            if(result==""||result==null){
                return new Resp("400","查询返回的数据为null");
            }
            message=JSONObject.fromObject(result).get("message");
        } catch (Exception e) {
            log.info("删除钱包出错.");
        }
        return Resp.success(message);
    }

    /**
     * 传入token,获取里面的用户名
     * @param token
     * @return
     */
    public String getUsersName (String token) {
        JSONObject resp = JSONObject.fromObject(acccount.tokenVerify(token));
        JSONObject data = resp.getJSONObject("data");
        return data.getString("user_nickname");
    }
}
