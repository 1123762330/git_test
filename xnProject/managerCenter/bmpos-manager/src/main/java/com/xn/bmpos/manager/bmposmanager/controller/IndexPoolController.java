package com.xn.bmpos.manager.bmposmanager.controller;

import com.xn.bmpos.manager.bmposmanager.util.GetData;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class IndexPoolController {

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
    public Resp pay15Record (String coin, Integer currentPage, Integer pageSize) {
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("currentPage", currentPage.toString());
        getMap.put("pageSize", pageSize.toString());

        Object data = getData.getDataToObject(coin, getMap);
        return Resp.success(data);
    }


    /**
     * 小鸟矿池比特币算力图标(1个月的数据总)
     *
     * @return
     */
    @GetMapping(value = "/curve")
    public Resp hashrateCurve (String coin) {
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);

        Object data = getData.getDataToObject(coin, getMap);
        return Resp.success(data);
    }


    /**
     * 矿池统计
     *
     * @return
     */
    @GetMapping(value = "/pool")
    public Resp poolsHashrate (String coin) {
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);

        Object data = getData.getDataToObject(coin, getMap);
        return Resp.success(data);
    }

}
