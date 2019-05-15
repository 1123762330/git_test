package com.xn.bmpos.api.bmposapi.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xn.bmpos.api.bmposapi.common.http.HttpAPIService;
import com.xn.bmpos.api.bmposapi.domain.mapper.SettingMapper;
import com.xn.bmpos.api.bmposapi.domain.model.CoinIndexModel;
import com.xn.bmpos.api.bmposapi.domain.model.MachineWebModel;
import com.xn.bmpos.api.bmposapi.domain.model.Record;
import com.xn.bmpos.api.bmposapi.exception.ErrorCode;
import com.xn.bmpos.api.bmposapi.service.MachineService;
import com.xn.bmpos.api.bmposapi.service.RecordService;
import com.xn.bmpos.api.bmposapi.tool.RedisUtil;
import com.xn.bmpos.api.bmposapi.tool.Resp;
import com.xn.bmpos.api.bmposapi.tool.Utile;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 矿池数据
 */

@Controller
@RequestMapping("api")
@ResponseBody
public class BmposIndexController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private MachineService machineService;

    @Autowired
    private SettingMapper settingMapper;

    private SimpleDateFormat simpleDateTime = new SimpleDateFormat("yyMMdd");//转换年月日

    /**
     * 首页数据
     *
     * @return
     */
    @GetMapping("/index")
    public Resp getBmpolsIndexValue() {

        Map<String, CoinIndexModel> resultMap = new HashMap<>();

        //todo 从redis缓存中获取币种key
        Set<String> redisUtilSubKey = redisUtil.getSubKey("*STATE");
        List<String> redisKeyList = new ArrayList<>(redisUtilSubKey);

        redisKeyList.forEach(key -> {
            CoinIndexModel coinIndexModel = new CoinIndexModel();
            //todo 通过key获取value
            String coin = StringUtils.substringBefore(key, ":");
            JSONObject json = JSONObject.fromObject(redisUtil.get(key));

            coinIndexModel.setCoin(coin);
            coinIndexModel.setEarnings(0.1);
            coinIndexModel.setNetHashrate(json.getDouble("netHashrate"));
            coinIndexModel.setHashrate(json.getDouble("poolDifficulty"));
            coinIndexModel.setNetDifficulty(Utile.big2(json.getDouble("netDifficulty")));
            coinIndexModel.setMinerCount(json.getInt("OnlineMiners"));
            coinIndexModel.setMinersTotal(json.getInt("OnlineMill"));

            resultMap.put(coin, coinIndexModel);
        });

        String code = "200";
        String message = "获取成功";
        if (resultMap.size() == 0) {
            code = ErrorCode.SYSTEM_ERROR.toString();
            message = "获取失败";
        }
        return Resp.result(code, message, resultMap);
    }

    /**
     * 获取币种价格
     *
     * @return
     */
    @GetMapping("/price")
    public Resp getBmpolsCoinPrice() {
        Map<String, String> resultMap = Utile.getPrice();
        return Resp.success(resultMap);
    }

    @GetMapping("/minners")
    public Resp getMinners() {
        List<MachineWebModel> machineWebModelList = machineService.querMergeAll();
        return Resp.success(machineWebModelList);
    }

    /**
     * 矿池统计
     *
     * @return
     */
    @RequestMapping("/pool")
    public Resp poolsStatistics(String coin) {
        Map<String, Object> resultMap = new HashMap<>();
        String bigKey = coin.toUpperCase() + ":POOL:STATE";
        String value = (String) redisUtil.get(bigKey);
        JSONObject jsonObject = JSONObject.fromObject(value);
        double netHashrate = jsonObject.getDouble("netHashrate");//全网算力
        double netDifficulty = jsonObject.getDouble("netDifficulty");//全网难度
        Object usernameTotal = jsonObject.get("usernameTotal");//矿机总数
        Object usersTotal = jsonObject.get("usersTotal");//旷工总数
        Object blocks = jsonObject.get("blocks");
        resultMap.put("netHashrate", netHashrate);
        resultMap.put("netDifficulty", netDifficulty);
        resultMap.put("usernameTotal", usernameTotal);
        resultMap.put("usersTotal", usersTotal);
        resultMap.put("blocks", blocks);
        return Resp.success(resultMap);
    }

    /**
     * 小鸟矿池比特币算力图标(1个月的数据总)
     * 首先通过模糊查询获取31天的大键集合
     * 然后遍历大键集合取出每一天的数据
     * 接下来遍历每一天的数据,通过小key去value,累加difficulty
     * 然后存入map集合返回
     *
     * @return
     */
    @RequestMapping("/curve")
    public Resp hashrateCurve(String coin) {
        SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyyMMdd");//取出年份月份天做小Key
        SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");//取出年份月份天做小Key
        Map<String, Object> resultMap = new HashMap<>();

        try {
            String bigKey = coin.toUpperCase() + ":POOL:SHARE:*";
            Set<String> subKey = redisUtil.getSubKey(bigKey);//大键集合
            Double difficulty = 0.00;
            for (String str : subKey) {//遍历大键得每一天的数据
                Map<Object, Object> map = redisUtil.hmget(str);
                Set<Object> keySet = map.keySet();//小key集合
                for (Object key : keySet) {//遍历小键取出vaule累计得一天的总算力
                    String keyStr = String.valueOf(key);//小key
                    Object value = redisUtil.hget(str, keyStr);
                    difficulty += Double.parseDouble(value.toString());
                }
                String time = "20" + str.substring(str.lastIndexOf(":") + 1);
                Date dateStr = simpleDateFormat1.parse(time);
                String dateTime = simpleDateFormat2.format(dateStr);
                resultMap.put(dateTime, difficulty);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Resp.success(resultMap);
    }


    @Autowired
    private RecordService recordService;

    /**
     * 显示近3天的交易记录并分页
     * @param currentPage
     * @param pageSize
     * @return
     */
    @RequestMapping("/pay")
    public Resp payRecord(@RequestParam("currentPage") Integer currentPage,@RequestParam("pageSize")Integer pageSize) {
        PageInfo<Record> recordList = recordService.findPayRecord(currentPage,pageSize);
        return Resp.success(recordList);
    }



    /**
     * 返回矿池24小时数据
     *
     * @param coin
     * @return
     */
    @GetMapping("/get_pool_hashrate")
    public Resp getPoolHashrate(String coin) {
        Date time = new Date();
        String date = simpleDateTime.format(time);
        Map<Object, Object> hmget = redisUtil.hmget(coin.toUpperCase() + ":POOL:SHARE:" + date);
        return Resp.success(hmget);
    }

    /**
     * 返回当前难度和起付额度及费率
     *
     * @param coin
     * @return
     */
    @GetMapping("/get_now")
    public Resp getNow(String coin) {
        Map<String, Double> nowMap = new HashMap<>();
        Object object = redisUtil.get(coin.toUpperCase() + ":POOL:STATE");
        JSONObject jsonObject = JSONObject.fromObject(object);
        Double netDifficulty = jsonObject.getDouble("netDifficulty");
        Double fee = jsonObject.getDouble("fee");
        String string = settingMapper.findValueByKey("confirmed");
        Double confirmed = Double.valueOf(string);
        nowMap.put("netDifficulty", netDifficulty);
        nowMap.put("fee", fee);
        nowMap.put("confirmed", confirmed);
        return Resp.success(nowMap);
    }


    @GetMapping("/poolStart")
    public Resp getMiners() {
        List<CoinIndexModel> results = new ArrayList<>();
        //todo 从redis缓存中获取币种key
        Set<String> redisUtilSubKey = redisUtil.getSubKey("*STATE");
        List<String> redisKeyList = new ArrayList<>(redisUtilSubKey);
        redisKeyList.forEach(key -> {
            CoinIndexModel coinIndexModel = new CoinIndexModel();
            //todo 通过key获取value
            String coin = StringUtils.substringBefore(key, ":");
            JSONObject json = JSONObject.fromObject(redisUtil.get(key));
            coinIndexModel.setCoin(coin);
            coinIndexModel.setNetDifficulty(Utile.big2(json.getDouble("netDifficulty")));
            results.add(coinIndexModel);
        });

        String code = "200";
        String message = "获取成功";
        if (results.size() == 0) {
            code = ErrorCode.SYSTEM_ERROR.toString();
            message = "获取失败";
        }
        return Resp.result(code, message, results);
    }

}
