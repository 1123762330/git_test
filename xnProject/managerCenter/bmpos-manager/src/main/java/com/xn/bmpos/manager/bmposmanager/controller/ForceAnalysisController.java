package com.xn.bmpos.manager.bmposmanager.controller;

import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import com.xn.bmpos.manager.bmposmanager.util.GetData;
import com.xn.bmpos.manager.bmposmanager.util.OkHttpUtils;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
@Slf4j
public class ForceAnalysisController {
    @Autowired
    private GetData getData;

    @Autowired
    AccountAPI accountAPI;

    @Autowired
    private WatchWalletController watchWalletController;

    private final Logger logger = LoggerFactory.getLogger(WatchWalletController.class);

    /**
     * 个人中心 算力分析
     *
     * @return
     */
    @GetMapping(value = "/account")
    public Resp forceAnalysis(String address, String coin) {
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("address", address);

        Object data = getData.getDataToObject(coin,getMap);
        return Resp.success(data);
    }

    /**
     * 账户交易分页显示,只显示最近3天
     *
     * @return
     */
    @GetMapping(value = "/collection")
    public Resp collection15Record(Integer currentPage,Integer pageSize,String address,String coin) {
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("address", address);
        getMap.put("currentPage", currentPage.toString());
        getMap.put("pageSize", pageSize.toString());

        Object data = getData.getDataToObject(coin,getMap);
        return Resp.success(data);
    }


    /**
     * 单台矿机算力分析图
     * address 钱包地址
     * minner 矿机名称
     *
     * @return
     */
    @GetMapping(value = "/banlanceshareByOne")
    public Resp balanceShares(String coin, String address, String minner) {
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("address", address);
        getMap.put("minner", minner);

        Object data = getData.getDataToObject(coin,getMap);
        return Resp.success(data);
    }



    /**
     * 查询子账户列表和对应的币种列表
     * @param
     * @return
     */
    @GetMapping(value = "/findUsersAndCoins")
    public Resp findUsersAndCoins() {
        Object data = accountAPI.findUsersAndCoins().get("data");
        return Resp.success(data);
    }

    /**
     * 首页通过钱包地址和币种进行查询
     * 如果数据库中有对应的币种和钱包地址
     * 那么就跳到登录页面
     * 如果没有,那就直接显示数据
     * @param address
     * @param coin
     * @return
     */
    @GetMapping(value = "/findDataByAddress")
    public Resp findDataByAddress(@RequestParam("address") String address, @RequestParam("coin") String coin) {
        JSONObject dataByAddress =  accountAPI.findDataByAddress(address, coin);
        Object data = dataByAddress.get("data");
        if(data!=null){
            log.info("跳转到登录页面");
            return Resp.success("用户已注册,请先登录!",data);
        }else {
            Resp resp = forceAnalysis(address, coin);
            return resp;
        }
    }



}
