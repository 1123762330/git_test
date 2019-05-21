package com.xn.bmpos.manager.bmposmanager.feign;

import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 短信验证码 cloud
 */
@Component
@FeignClient(name = "sms-service")
public interface SmsApi {

    @RequestMapping(value = "/api/code/sms", method = RequestMethod.GET)
    JSONObject getByAidAndCurrency(@RequestParam("mobile") String mobile, @RequestParam("time") String time);

    @RequestMapping(value = "/api/code/smsVerifi", method = RequestMethod.GET)
    JSONObject smsVerifi(@RequestParam("mobile")String mobile, @RequestParam("code")String code);
}
