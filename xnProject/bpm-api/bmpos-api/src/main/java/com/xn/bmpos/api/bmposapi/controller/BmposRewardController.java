package com.xn.bmpos.api.bmposapi.controller;

import com.xn.bmpos.api.bmposapi.common.http.HttpAPIService;
import com.xn.bmpos.api.bmposapi.domain.dao.Machine;
import com.xn.bmpos.api.bmposapi.domain.model.RewardModel;
import com.xn.bmpos.api.bmposapi.feign.RewadAPI;
import com.xn.bmpos.api.bmposapi.tool.Resp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.net.httpserver.HttpServerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 奖励中心
 */

@Controller
@ResponseBody
@RequestMapping("api")
public class BmposRewardController {
    @Autowired
    private HttpAPIService httpAPIService;
    @Autowired
    private RewadAPI rewadAPI;

    @Value("${httpClient.reward-api}")
    private String httpRewadApi;

    @RequestMapping("/reward")
    public Resp getRewardInfo(String address) {
        String code = "200";
        String message = "请求成功";
        List<RewardModel> resultList = new ArrayList<>();
        //todo 请求
        Map<String, Object> map = new HashMap<>();
        map.put("address", address);
        JSONObject resultJson = null;
        resultJson = rewadAPI.getReward(address);

        if (resultJson != null) {
            JSONArray jsonArray = resultJson.getJSONArray("data");
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RewardModel rewardModel = new RewardModel();

                rewardModel.setTime(jsonObject.getString("time"));
                rewardModel.setAddress(jsonObject.getString("accountAddress"));
                String mark = jsonObject.getString("mark");

                //本次支付
                String balance = StringUtils.substringBetween(mark, ":");
                //还剩余额
                String yu = StringUtils.substringAfterLast(mark, ":");

                rewardModel.setBalance(Double.valueOf(balance));
                rewardModel.setYue(Double.valueOf(yu));
                rewardModel.setTxId(jsonObject.getString("accountAddress"));
                resultList.add(rewardModel);
            }
        }
        return Resp.result(code, message, resultList);
    }


}
