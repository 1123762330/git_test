package com.xn.console.bmposconsole.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xn.console.bmposconsole.dao.feign.RewardApiFeign;
import com.xn.console.bmposconsole.dao.model.RewardFeignModel;
import com.xn.console.bmposconsole.dao.model.RewardModel;
import com.xn.console.bmposconsole.exception.ErrorCode;
import com.xn.console.bmposconsole.tools.Resp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api")
public class RewardController {

    @Autowired
    private RewardApiFeign rewardApiFeign;

    /**
     * 查询币种
     */
    @GetMapping("/reward")
    public Resp getReward() {
        JSONObject resultJson = rewardApiFeign.getReward();
        RewardFeignModel rewardFeignModel = new Gson().fromJson(resultJson.toJSONString(), RewardFeignModel.class);
        return Resp.success(rewardFeignModel.getData());
    }

    /**
     * 添加奖励币种设置
     *
     * @return
     */
    @PostMapping("/insertReward")
    public Resp InsertRewardSetting(RewardModel rewardModel) {

        if (StringUtils.isEmpty(rewardModel.getCoin()) || StringUtils.isEmpty(rewardModel.getEnabled())
                || StringUtils.isEmpty(rewardModel.getPostUrl()) || rewardModel.getThreshold() == 0) {
            return Resp.fail(ErrorCode.ISNotCanShu);
        }

        JSONObject object = rewardApiFeign.InsertRewardSetting(rewardModel.getCoin(), rewardModel.getThreshold(),
                rewardModel.getRewardCount(), rewardModel.getPostUrl(), rewardModel.getEnabled());

        if (object != null) {
            RewardFeignModel rewardFeignModel = new Gson().fromJson(object.toJSONString(), RewardFeignModel.class);
            if (rewardFeignModel.isSuccess()) {
                return Resp.success(rewardFeignModel.getMessage());
            }
        }
        return Resp.fail(ErrorCode.SYSTEM_ERROR);
    }

    /**
     * 修改奖励中心配置
     */

    @PostMapping("/updateReward")
    public Resp updateReward(RewardModel rewardModel) {
        if (rewardModel.getId() == 0) {
            return Resp.fail(ErrorCode.ISNotCanShu);
        }
        JSONObject result = rewardApiFeign.updateReward(rewardModel.getId(), rewardModel.getCoin(), rewardModel.getThreshold(),
                rewardModel.getRewardCount(), rewardModel.getPostUrl(), rewardModel.getEnabled());
        if (result != null) {
            RewardFeignModel rewardFeignModel = new Gson().fromJson(result.toJSONString(), RewardFeignModel.class);
            if (rewardFeignModel.isSuccess()) {
                return Resp.success(rewardFeignModel.getMessage());
            }
        }
        return Resp.fail(ErrorCode.SYSTEM_ERROR);
    }


}
