package com.xn.console.bmposconsole.controller;

import com.google.gson.Gson;
import com.xn.console.bmposconsole.dao.feign.AccountAPI;
import com.xn.console.bmposconsole.dao.model.CoinSetting;
import com.xn.console.bmposconsole.dao.model.FeignModel;
import com.xn.console.bmposconsole.dao.model.SaleAddressAll;
import com.xn.console.bmposconsole.exception.ErrorCode;
import com.xn.console.bmposconsole.tools.Resp;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 钱包权限
 */
@RestController
@RequestMapping("api")
@Slf4j
public class AddressSettingController {

    @Autowired
    private AccountAPI accountAPI;

    /**
     * 查看所有钱包的相关信息
     *
     * @return
     */
    @GetMapping(value = "/findSaleAddress")
    public Resp findSaleAddress () {
        JSONObject saleAddress = accountAPI.findSaleAddress();
        log.info("saleAddress:" + saleAddress);
        if (saleAddress.getInt("status") != 200) {
            return new Resp("400", "查询返回的数据为null");
        }
        FeignModel rewardModel = new Gson().fromJson(saleAddress.toString(), FeignModel.class);
        Map<String, List<SaleAddressAll>> groupBy = new HashMap<>();
        if (rewardModel != null) {
            JSONArray jsonArray = JSONArray.fromObject(rewardModel.getData());
            List<SaleAddressAll> list = (List) JSONArray.toCollection(jsonArray, SaleAddressAll.class);
             groupBy = list.stream().collect(Collectors.groupingBy(SaleAddressAll::getUsers));
        }
        return Resp.success(groupBy);
    }

    /**
     * 修改钱包级别
     *
     * @param rank     钱包级别
     * @param walletId 钱包Id
     * @return
     */
    @GetMapping(value = "/updateWalletRank")
    public Resp updateWalletRank (@RequestParam("rank") Integer rank, @RequestParam("walletId") Integer walletId) {
        if (StringUtils.isEmpty(walletId.toString()) || StringUtils.isEmpty(walletId.toString())) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        JSONObject jsonObject = accountAPI.updateWalletRank(rank, walletId);
        log.info("jsonObject:"+jsonObject);
        if (jsonObject.getInt("status") != 200) {
            return new Resp("400", "查询返回的数据为null");
        }
        FeignModel rewardModel = new Gson().fromJson(jsonObject.toString(), FeignModel.class);
        return Resp.success(rewardModel.getData());
    }

    /**
     * 修改之前回显数据
     *
     * @param walletId 钱包Id
     * @return
     */
    @GetMapping("/selectWallet")
    public Resp selectWallet (@RequestParam("walletId") Integer walletId) {
        if (StringUtils.isEmpty(walletId.toString())) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        JSONObject jsonObject = accountAPI.selectWalletByOne(walletId);
        if (jsonObject.getInt("status") != 200) {
            return new Resp("400", "查询返回的数据为null");
        }
        FeignModel rewardModel = new Gson().fromJson(jsonObject.toString(), FeignModel.class);
        return Resp.success(rewardModel);
    }
}
