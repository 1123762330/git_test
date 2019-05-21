package com.xn.bmpos.manager.bmposmanager.controller;

import com.google.gson.Gson;
import com.xn.bmpos.manager.bmposmanager.domain.model.FeignModel;
import com.xn.bmpos.manager.bmposmanager.exception.ErrorCode;
import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import com.xn.bmpos.manager.bmposmanager.util.GetData;
import com.xn.bmpos.manager.bmposmanager.util.OkHttpUtils;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

import static org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers.data;

@RestController
@RequestMapping("api")
@Slf4j
public class ForceAnalysisController extends BaskController {
    @Autowired
    private GetData getData;

    @Autowired
    AccountAPI accountAPI;


    /**
     * 个人中心 算力分析
     *
     * @return
     */
    @GetMapping(value = "/account")
    public Resp forceAnalysis(String address, String coin) {
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(coin)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }

        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("address", address);

        Object data = getData.getDataToObject(coin, getMap);
        if(data==null){
            return new Resp("400","查询返回的数据为null");
        }
        FeignModel feignModel = new Gson().fromJson(data.toString(), FeignModel.class);

        return Resp.success(feignModel.getData());
    }

    /**
     * 账户交易分页显示,只显示最近3天
     *
     * @return
     */
    @GetMapping(value = "/collection")
    public Resp collection15Record( @RequestParam(defaultValue = "1")Integer currentPage,  @RequestParam(defaultValue = "15")Integer pageSize, String address, String coin) {
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(coin)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("address", address);
        getMap.put("currentPage", currentPage.toString());
        getMap.put("pageSize", pageSize.toString());

        Object data = getData.getDataToObject(coin, getMap);
        if(data==null){
            return new Resp("400","查询返回的数据为null");
        }
        FeignModel feignModel = new Gson().fromJson(data.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
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
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(coin)||StringUtils.isEmpty(minner)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("address", address);
        getMap.put("minner", minner);

        Object data = getData.getDataToObject(coin, getMap);
        if(data==null){
            return new Resp("400","查询返回的数据为null");
        }
        FeignModel feignModel = new Gson().fromJson(data.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }


    /**
     * 查询子账户列表和对应的币种列表
     *
     * @param
     * @return
     */
    @GetMapping(value = "/findUsersAndCoins")
    public Resp findUsersAndCoins(HttpSession session) {
        Integer userId = getUidfromSession(session);
        Object data = accountAPI.findUsersAndCoins(userId);
        if(data==null){
            return new Resp("400","查询返回的数据为null");
        }
        FeignModel feignModel  = new Gson().fromJson(data.toString(),FeignModel.class);

        return Resp.success(feignModel.getData());
    }

    /**
     * 首页通过钱包地址和币种进行查询
     * 如果数据库中有对应的币种和钱包地址
     * 那么就跳到登录页面
     * 如果没有,那就直接显示数据
     *
     * @param address
     * @param coin
     * @return
     */
    @GetMapping(value = "/findDataByAddress")
    public Resp findDataByAddress(@RequestParam("address") String address, @RequestParam("coin") String coin) {
        if (StringUtils.isEmpty(address) || StringUtils.isEmpty(coin)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        JSONObject dataByAddress = accountAPI.findDataByAddress(address, coin);
        if(dataByAddress.getInt("state")!=200){
            return new Resp("400","查询返回的数据为null");
        }
        JSONObject data = dataByAddress.getJSONObject("data");
        //获取IsAccount的具体值,判断是1还是0
        int isAccount = data.getInt("isAccount");
        Object accountData = JSONObject.toBean(data);
        Resp respData = null;
        if (accountData != null) {
            log.info("跳转到登录页面");
            if (isAccount == 1) {
                log.info("要登录");
                respData = new Resp("0", "用户已存在,请先登录");
            } else if (isAccount == 0) {
                /*Resp resp = forceAnalysis(address, coin);
                Object successData = resp.getData();
                log.info("successData"+successData);*/
                respData = new Resp("200", "可以直接进行查询");
            }
            return respData;
        } else {
            return new Resp("1", "钱包不存在");
        }
    }


}
