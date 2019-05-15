package com.xn.bmpos.manager.bmposmanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xn.bmpos.manager.bmposmanager.domain.dao.Setting;
import com.xn.bmpos.manager.bmposmanager.domain.model.IndexCoinModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.IndexNodeModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.MachineWebModel;
import com.xn.bmpos.manager.bmposmanager.service.MachineService;
import com.xn.bmpos.manager.bmposmanager.service.SettingService;
import com.xn.bmpos.manager.bmposmanager.util.MonUtils;
import com.xn.bmpos.manager.bmposmanager.util.OkHttpUtils;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("api")
public class IndexCoinController {

    @Autowired
    private SettingService settingService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MachineService machineService;


    @GetMapping(value = "/index")
    public Resp getTest () {
        //todo 从数据库中获取配置（有多少个币种）
        List<Setting> settings = settingService.queryAllCoin();
        //todo 循环请求
        String path = request.getServletPath();
        List<IndexCoinModel> resultList = new ArrayList<>();
        settings.forEach(setting -> {
            String url = setting.getRpchost() + path;
            String coin = setting.getName();//对应的币种
            Integer unit = setting.getUnit();//换算的单位
            String str = OkHttpUtils.get(url, null);
            if (!StringUtils.isEmpty(str)) {
                JSONObject itemJSONObj = JSONObject.parseObject(str).getJSONObject("data");
                if (itemJSONObj.getJSONObject(coin.toUpperCase()) != null) {//判断API里面有没有这个币种
                    itemJSONObj.getJSONObject(coin.toUpperCase()).put("unit", unit);//有的话就将该币种换算单位加入json字符串中
                }

                Map<String, IndexCoinModel> itemMap = JSONObject.toJavaObject(itemJSONObj, Map.class);
                for (Map.Entry<String, IndexCoinModel> entry : itemMap.entrySet()) {
                    resultList.add(entry.getValue());
                }
            }
        });
        return Resp.result("200", "ok", resultList);

    }


    /**
     * 获取币种价格
     *
     * @return
     */
    @GetMapping("/price")
    public Resp getBmpolsCoinPrice () {
        boolean isTrue = true;
        Map<String, String> resultMap = new HashMap<>();
        while(isTrue){
            resultMap = MonUtils.getPrice();
            if(!resultMap.isEmpty()){
                isTrue = false;
            }
        }
        return Resp.success(resultMap);
    }

    /**
     * 矿机收益
     */
    @GetMapping("/poolStart")
    public Resp getMinners () {
        //todo  从api 获取算力
        String path = request.getServletPath();
        List<MachineWebModel> machineWebModelList = machineService.querMergeAll(path);
        return Resp.success(machineWebModelList);
    }

    /**
     * 返回矿池24小时数据
     *
     * @param coin
     * @return
     */
    @GetMapping("/get_pool_hashrate")
    public Resp getPoolHashrate (String coin) {
        String path = request.getServletPath();
        Map<String, Object> itemMap = null;
        if (coin != null) {
            Setting setting = settingService.selectCoin(coin);
            if (setting != null) {
                String url = setting.getRpchost() + path;
                Map<String, String> getMap = new HashMap<>();
                getMap.put("coin", coin);
                String str = OkHttpUtils.get(url, getMap);
                JSONObject jsonObject = JSONObject.parseObject(str);
                itemMap = JSONObject.toJavaObject(jsonObject.getJSONObject("data"), Map.class);
            }
        }
        return Resp.success(itemMap);
    }

    /**
     * .返回节点
     *
     * @return
     */
    @GetMapping("/get_now")
    public Resp getNode (String coin) {
        String path = request.getServletPath();
        IndexNodeModel nodeModel = new IndexNodeModel();
        if (!StringUtils.isEmpty(coin)) {
            Setting setting = settingService.selectCoin(coin);
            if (setting != null) {
                String url = setting.getRpchost() + path;
                Map<String, String> getMap = new HashMap<>();
                getMap.put("coin", coin.toUpperCase());
                String str = OkHttpUtils.get(url, getMap);
                if (!StringUtils.isEmpty(str)) {
                    JSONObject jsonObject = JSON.parseObject(str).getJSONObject("data");
                    nodeModel = JSONObject.toJavaObject(jsonObject, IndexNodeModel.class);
                    nodeModel.setNode(setting.getNode());
                }
            }
        }
        return Resp.success(nodeModel);
    }

    /**
     * 获取所有的币种
     *
     * @return
     */
    @GetMapping("/getAllCoin")
    public Resp getAllCoin () {
        List<String> coins = settingService.queryAllCoinType();
        return Resp.success(coins);
    }


}
