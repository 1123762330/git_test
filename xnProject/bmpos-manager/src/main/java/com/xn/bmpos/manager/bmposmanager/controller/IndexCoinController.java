package com.xn.bmpos.manager.bmposmanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinSetting;
import com.xn.bmpos.manager.bmposmanager.domain.model.FeignModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.IndexCoinModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.IndexNodeModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.MachineWebModel;
import com.xn.bmpos.manager.bmposmanager.service.CoinTypeService;
import com.xn.bmpos.manager.bmposmanager.service.MachineService;
import com.xn.bmpos.manager.bmposmanager.service.CoinSettingService;
import com.xn.bmpos.manager.bmposmanager.util.GetData;
import com.xn.bmpos.manager.bmposmanager.util.OkHttpUtils;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("api")
public class IndexCoinController {

    @Autowired
    private CoinSettingService coinSettingService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MachineService machineService;

    @Autowired
    private CoinTypeService coinTypeService;

    @Autowired
    private GetData getData;

    @GetMapping(value = "/index")
    public Resp getTest() {
        //todo 从数据库中获取配置（有多少个币种）
        List<CoinSetting> settings = coinSettingService.queryAllCoin();
        //todo 循环请求
        String path = request.getServletPath();
        List<IndexCoinModel> resultList = new ArrayList<>();
        settings.forEach(setting -> {
            String url = setting.getRpchost() + path;
            String coin = setting.getName();//对应的币种
            String unit = setting.getUnit(); //对应的单位
            Integer scale = setting.getScale();//换算数值
            String str = OkHttpUtils.get(url, null);

            if (!StringUtils.isEmpty(str)) {
                JSONObject itemJSONObj = JSONObject.parseObject(str).getJSONObject("data");
                if (itemJSONObj.getJSONObject(coin.toUpperCase()) != null) {//判断API里面有没有这个币种
                    itemJSONObj.getJSONObject(coin.toUpperCase()).put("unit", unit);//有的话就将该币种换算单位加入json字符串中
                    itemJSONObj.getJSONObject(coin.toUpperCase()).put("scale", scale);//有的话就将该币种换算单位加入json字符串中
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
    public Resp getBmpolsCoinPrice() {
        Map<String, String> resultMap = new HashMap<String, String>();
        resultMap = coinTypeService.findAllCoin();
        return Resp.success(resultMap);
    }

    /**
     * 矿机收益
     */
    @GetMapping("/poolStart")
    public Resp getMinners() {
        //todo  从api 获取算力
        String path = request.getServletPath();
        List<MachineWebModel> machineWebModelList = machineService.querMergeAll(path);
        return Resp.success(machineWebModelList);
    }

    /**
     * 币种矿机收益
     */
    @GetMapping("/poolStartCoin")
    public Resp getMinnersByCoin(String coin) {
        List<MachineWebModel> machineWebModelList = new ArrayList<MachineWebModel>();
        //查询币种id
        int coinId = machineService.selectCoinName(coin);
        if (coinId > 0) {
            //通过币种id 查询此币种 信息
            machineWebModelList = machineService.queryMachineAllByCoin("/api/poolStart", coinId);
        } else {
            System.out.println("矿机收益排行榜查询失败");
            return Resp.success("获取矿机信息失败", null);
        }
        return Resp.success(machineWebModelList);
    }

    /**
     * .返回节点
     *
     * @return
     */
    @GetMapping("/get_now")
    public Resp getNode(String coin) {
        String path = request.getServletPath();
        IndexNodeModel nodeModel = new IndexNodeModel();
        if (!StringUtils.isEmpty(coin)) {
            CoinSetting setting = coinSettingService.selectCoin(coin);
            if (setting != null) {
                String url = setting.getRpchost() + path;
                Map<String, String> getMap = new HashMap<>();
                getMap.put("coin", coin.toUpperCase());
                String str = OkHttpUtils.get(url, getMap);
                if (!StringUtils.isEmpty(str)) {
                    JSONObject jsonObject = JSON.parseObject(str).getJSONObject("data");
                    nodeModel = JSONObject.toJavaObject(jsonObject, IndexNodeModel.class);
                    nodeModel.setNode(setting.getNode());
                    nodeModel.setUnit(setting.getUnit());
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
    public Resp getAllCoin() {
        List<String> coins = coinSettingService.queryAllCoinType();
        return Resp.success(coins);
    }

    /**
     * 获取币种对应的图片地址
     */
    @GetMapping("/getPicture")
    public Resp getPicture() {
        List<CoinSetting> coinSetting = coinSettingService.queryAllCoin();
        Map<String, Object> resultMap = new HashMap<>();
        coinSetting.forEach(coin -> {
            resultMap.put(coin.getName(), coin.getImage());
        });
        return Resp.success(resultMap);
    }


    /**
     * 获取单位
     */
    @GetMapping(value = "/getunit")
    public Resp findLogin() {
        List<CoinSetting> coinSetting = coinSettingService.queryAllCoin();
        Map<String, Object> resultMap = new HashMap<>();
        coinSetting.forEach(coin -> {
            resultMap.put(coin.getName(), coin.getUnit());
        });
        return Resp.success(resultMap);
    }

    /**
     * 返回矿池24小时数据
     *
     * @param coin
     * @return
     */
    @GetMapping("/get_pool_hashrate")
    public Resp getPoolHashrate(String coin) {
        HashMap<String, Object> getMap = new HashMap<>();
        getMap.put("coin",coin);

        Object data = getData.getDataToObject(coin, getMap);
        FeignModel feignModel = new Gson().fromJson(data.toString(), FeignModel.class);

        return Resp.success(feignModel.getData());
    }

}
