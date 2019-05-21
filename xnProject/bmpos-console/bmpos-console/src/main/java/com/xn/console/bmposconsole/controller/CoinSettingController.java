package com.xn.console.bmposconsole.controller;

import com.xn.console.bmposconsole.dao.model.CoinSetting;
import com.xn.console.bmposconsole.exception.ErrorCode;
import com.xn.console.bmposconsole.service.CoinSettingService;
import com.xn.console.bmposconsole.tools.Resp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


/**
 * todo bmpos-manager  币种设置
 */

@RestController
@RequestMapping("api")
@Slf4j
public class CoinSettingController {

    @Autowired
    private CoinSettingService coinSettingService;

    /**
     * 查询t_coin_setting表中所有的数据
     *
     * @return
     */
    @GetMapping(value = "/findAllCoinSetting")
    public Resp findAllCoinSetting () {
        List<CoinSetting> allCoinSetting = coinSettingService.queryAllCoin();
        log.info("allCoinSetting" + allCoinSetting);
        return Resp.success(allCoinSetting);
    }

    /**
     * 添加新的币种服务器
     *
     * @param coinSetting
     * @return
     */
    @GetMapping(value = "/addCoinToSetting")
    public Resp addCoinToSetting (CoinSetting coinSetting) {
        coinSettingService.addCoinToSetting(coinSetting);
        return new Resp("200", "添加成功");
    }

    /**
     * 修改币种服务器
     *
     * @param coinSetting
     * @return
     */
    @GetMapping(value = "/updateCoinToSetting")
    public Resp updateCoinToSetting (CoinSetting coinSetting) {
        coinSettingService.updateCoinToSetting(coinSetting);
        return new Resp("200", "修改成功");
    }

    /**
     * 修改前回显数据
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/findCoinbyId")
    public Resp findCoinbyId (Integer id) {
        if (StringUtils.isEmpty(id.toString())) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        CoinSetting coinbyId = coinSettingService.findCoinbyId(id);
        return Resp.success(coinbyId);
    }

    /**
     * 创建t_coin_setting币种默认
     * @return
     */
    @GetMapping(value = "/getCoinSettingDefaultValue")
    public Resp getCoinSettingDefaultValue () {
        HashMap<String, Object>  defaultValueMap= new LinkedHashMap<>();
        defaultValueMap.put("name","ltc");
        defaultValueMap.put("fee","1");
        defaultValueMap.put("blockPrice","2.65");
        defaultValueMap.put("rpchost","http://");
        defaultValueMap.put("node","ltc.xnpool.cn:3333,ltc.xnpool.cn:25,ltc.xnpool.cn:8888");
        defaultValueMap.put("value","xx币种服务器");
        defaultValueMap.put("image","http://192.168.0.44:8989/extStatic/images/coin-ltc.png");
        defaultValueMap.put("unit","H");
        defaultValueMap.put("scale","1");
        defaultValueMap.put("isAccept","Y");
        return Resp.success(defaultValueMap);
    }

}
