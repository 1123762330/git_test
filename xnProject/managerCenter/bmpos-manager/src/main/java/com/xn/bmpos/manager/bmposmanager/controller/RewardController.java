package com.xn.bmpos.manager.bmposmanager.controller;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.xn.bmpos.manager.bmposmanager.domain.model.FeignModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.RewardModel;
import com.xn.bmpos.manager.bmposmanager.exception.ErrorCode;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import com.xn.bmpos.manager.bmposmanager.feign.RewardApi;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * @Author: zly
 * @Date: 2019/5/10 17:43
 */

@RestController
@RequestMapping("api")
public class RewardController {

    @Autowired
    private RewardApi rewardApi;

    /**
     * 奖励中心
     *
     * @return
     */
    @GetMapping(value = "/reward")
    public Resp getRewardInfo(String coin, String address) {

        JSONObject jsonObject = rewardApi.getByAidAndCurrency(coin, address);
        FeignModel feignModel = new Gson().fromJson(jsonObject.toString(), FeignModel.class);
        if (feignModel.isSuccess()) {
            List<RewardModel> rewardModelList = new ArrayList<>();
            //todo
            Object object = feignModel.getData();
            JSONArray array = JSONArray.fromObject(object);
            for (int i = 0; i < array.size(); i++) {
                JSONObject rewarObj = array.getJSONObject(i);
                RewardModel rewardModel = new Gson().fromJson(rewarObj.toString(), RewardModel.class);
                rewardModelList.add(rewardModel);
            }

            Map<String, Object> extraMap = new HashMap<>();
            //todo 从list 获取总收益  == 一共转给多少币
            Double revenue = rewardModelList.stream().mapToDouble(RewardModel::getIspaid).sum();//和
            //获取最近一次的赠送记录

            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            if (rewardModelList.size() > 1) {
                //list 集合倒叙排序
                rewardModelList.sort((a1, a2) -> {
                    try {
                        return df.parse(sdf.format(a2.getTime())).compareTo(df.parse(sdf.format(a1.getTime())));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    return 1;
                });
            }
            //得到list第一个值就是最近的交易记录
            RewardModel rewardModel = rewardModelList.get(0);
            extraMap.put("ispaid",rewardModel.getIspaid());// 已经支付的
            extraMap.put("isNotPaid",rewardModel.getIsNotPaid()); //待支付
            extraMap.put("revenue",revenue); //总支付
            return Resp.success("获取数据成功",rewardModelList,extraMap);
        }
        return Resp.fail(ErrorCode.SYSTEM_ERROR, "该账户暂无奖励", null);
    }


}
