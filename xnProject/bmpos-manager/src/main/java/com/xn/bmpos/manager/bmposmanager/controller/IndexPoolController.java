package com.xn.bmpos.manager.bmposmanager.controller;

import com.google.gson.Gson;
import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinSetting;
import com.xn.bmpos.manager.bmposmanager.domain.model.FeignModel;
import com.xn.bmpos.manager.bmposmanager.exception.ErrorCode;
import com.xn.bmpos.manager.bmposmanager.util.GetData;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
@Slf4j
public class IndexPoolController extends BaceController {

    @Autowired
    private GetData getData;

    @Autowired
    private HttpServletRequest request;

    /**
     * 支付记录分页显示所有,默认显示第1页15条
     *
     * @return
     */
    @GetMapping(value = "/pay")
    public Resp pay15Record(String coin, @RequestParam(defaultValue = "1") Integer currentPage, @RequestParam(defaultValue = "15") Integer pageSize) {
        if (StringUtils.isEmpty(coin)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("currentPage", currentPage.toString());
        getMap.put("pageSize", pageSize.toString());

        Object data = getData.getDataToObject(coin, getMap);
        if(data==null){
            log.info("从用户中心返回的数据为空");
            return new Resp("400","从用户中心返回的数据为空");
        }
        FeignModel feignModel = new Gson().fromJson(data.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }


    /**
     * 小鸟矿池比特币算力图标(1个月的数据总)
     *
     * @return
     */
    @GetMapping(value = "/curve")
    public Resp hashrateCurve(String coin) {
        if (StringUtils.isEmpty(coin)){
            return Resp.fail(ErrorCode.ISNotCanShu);
        }

        if (StringUtils.isEmpty(coin)){
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }

        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        Object data = getData.getDataToObject(coin, getMap);
        if(data==null){
            log.info("从用户中心返回的数据为空");
            return new Resp("400","从用户中心返回的数据为空");
        }
        FeignModel feignModel = new Gson().fromJson(data.toString(), FeignModel.class);
        CoinSetting setting = getUnit(coin);
        Map<String, Object> exit = new HashMap<>();
        exit.put("unit", setting.getUnit());
        return Resp.success("请求成功", feignModel, exit);
    }


    /**
     * 矿池统计
     *
     * @return
     */
    @GetMapping(value = "/pool")
    public Resp poolsHashrate(String coin) {
        if (StringUtils.isEmpty(coin)){
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        Map<String, Object> getMap = new HashMap<>();
        getMap.put("coin", coin);
        Object data = getData.getDataToObject(coin, getMap);
        if(data==null){
            log.info("从用户中心返回的数据为空");
            return new Resp("400","从用户中心返回的数据为空");
        }
        FeignModel feignModel = new Gson().fromJson(data.toString(), FeignModel.class);
        //todo 获取单位
        CoinSetting setting = getUnit(coin);
        getMap.put("unit", setting.getUnit());
        return Resp.success("数据请求成功", feignModel.getData(), getMap);
    }

}
