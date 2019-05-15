package com.xn.bmpos.api.bmposapi.controller;

import com.github.pagehelper.PageInfo;
import com.xn.bmpos.api.bmposapi.common.http.HttpAPIService;
import com.xn.bmpos.api.bmposapi.domain.model.Record;
import com.xn.bmpos.api.bmposapi.feign.AccountAPI;
import com.xn.bmpos.api.bmposapi.service.RecordService;
import com.xn.bmpos.api.bmposapi.tool.RedisUtil;
import com.xn.bmpos.api.bmposapi.tool.Resp;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("api")
@ResponseBody
public class forceAnalysisController {
    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpAPIService httpAPIService;

    private SimpleDateFormat simplehour = new SimpleDateFormat("yyMMdd:HH");
    private SimpleDateFormat simpleDate = new SimpleDateFormat("yyMMdd");

    @Autowired
    AccountAPI accountAPI;
    @Autowired
    private RecordService recordService;

    @Value("${httpClient.account-ip}")
    private String accountIp;

    /**
     * 个人中心 算力分析
     *
     * @return
     */
    @RequestMapping("/account")
    public Resp forceAnalysis(@RequestParam("address") String address, @RequestParam("coin") String coin) {
        Map<String, Object> resultMap = new HashMap<>();
        HashMap<String, Object> realDataMap = new HashMap<>();//真实算力线
        HashMap<String, Object> avgDataMap = new HashMap<>();//平均算力线
        HashMap<String, Object> uncountdataMap = new HashMap<>();//无效份额线
        HashMap<String, Object> vcountdataMap = new HashMap<>();//有效份额线
        HashMap<String, Object> paramMap = new HashMap<>();//参数集
        paramMap.put("address", address);
        paramMap.put("coin", coin);
        String user = null;
        JSONObject jsonObject = accountAPI.getUsernameByCoinAndAddress(coin,address);
        System.err.println(jsonObject);
        if(jsonObject.getInt("state")==200&&jsonObject.size()>1){
            user = jsonObject.get("data").toString();
        }else {
            return Resp.success("查询不到数据");
        }
        String bigKey = coin.toUpperCase() + ":ACCOUNT:STATE:" + user;
        Object blanace = redisUtil.hget(bigKey, "blanace");//余额
        Object unpaid = redisUtil.hget(bigKey, "unpaid");//待支付
        Object totalRevenue = redisUtil.hget(bigKey, "total");//总收益
        Set<Object> totaltSet = redisUtil.sGet(coin.toUpperCase() + ":TOTAL:" + user);//总矿机集合
        Integer totalSize = totaltSet.size();//总矿机数量
        Set<Object> onLineSet =  redisUtil.sGet(coin.toUpperCase() + ":ONLINE:" + user);//在线矿机数量
        Integer onLine = onLineSet.size();
        Integer offLineSize = null;
        if (totalSize >= onLine) {
            offLineSize = totalSize - onLine;//离线数量
        } else {
            offLineSize = 0;
        }
        Double sumDifficulty24 = Double.valueOf(redisUtil.hget(bigKey, "sumDifficulty24").toString());//24小时的总算力
        Double avgDifficulty24 = sumDifficulty24 / 24;//24小时的平均算力

        //有效份额和无效份额
        Map<Object, Object> vcointMap = redisUtil.hmget(coin.toUpperCase() + ":ACCOUNT:VCOUNT:" + user);
        Map<Object, Object> uncointMap = redisUtil.hmget(coin.toUpperCase() + ":ACCOUNT:UNCOUNT:" + user);
        Integer vcoint24 = 0;
        for (Object value : vcointMap.values()) {
            Integer count = Integer.valueOf(value.toString());
            vcoint24 += count;
        }
        Integer uncoint24 = 0;
        for (Object value : uncointMap.values()) {
            Integer count = Integer.valueOf(value.toString());
            uncoint24 += count;
        }

        System.out.println("====" + sumDifficulty24);
        //求实时算力
        String timeStr = simplehour.format(new Date());
        //String timeStr = "190430:12";
        String[] splits = timeStr.split(":");
        String bigKey2 = coin + ":ACC:SHIS:" + user + ":REAL:" + splits[0];
        Object realHashrate = redisUtil.hget(bigKey2, splits[1]);//实时算力
        resultMap.put("blanace", blanace);
        resultMap.put("unpaid", unpaid);
        resultMap.put("totalRevenue", totalRevenue);
        resultMap.put("onLine", onLine);
        resultMap.put("avgDifficulty24", avgDifficulty24);
        resultMap.put("realHashrate", realHashrate);
        resultMap.put("offLineSize", offLineSize);
        resultMap.put("totalSize", totalSize);
        resultMap.put("uncoint24", uncoint24);
        resultMap.put("vcoint24", vcoint24);

        //实时算力分析图(24小时)
        Map<Object, Object> realMap = redisUtil.hmget(coin.toUpperCase() + ":ACC:SHIS:" + user + ":REAL:" + splits[0]);
        realMap.forEach((keyStr, value) -> {
            realDataMap.put(keyStr.toString(), value);
        });

        //平均算力分析图(24小时)
        Map<Object, Object> avgMap = redisUtil.hmget(coin.toUpperCase() + ":ACC:SHIS:" + user + ":AVG:" + splits[0]);
        avgMap.forEach((keyStr, value) -> {
            avgDataMap.put(keyStr.toString(), value);
        });

        //份额无效分析图(24小时)
        uncointMap.forEach((keyStr2, value) -> {
            uncountdataMap.put(keyStr2.toString(), value);
        });

        //份额有效分析图(24小时)
        vcointMap.forEach((keyStr3, value) -> {
            vcountdataMap.put(keyStr3.toString(), value);
        });

        resultMap.put("realHashrate", realDataMap);
        resultMap.put("avgHashrate", avgDataMap);
        resultMap.put("uncount", uncountdataMap);
        resultMap.put("vcount", vcountdataMap);
        return Resp.success(resultMap);
    }

    /**
     * 账户交易显示所有并分页
     *
     * @return
     */

    @RequestMapping("/collection")
    public Resp collectionAllRecord(@RequestParam("currentPage") Integer currentPage,@RequestParam("pageSize")Integer pageSize,@RequestParam("address") String address, @RequestParam("coin") String coin) {
        PageInfo<Record> recordList = recordService.findCollectionAllRecord(currentPage,pageSize,address);
        return Resp.success(recordList);
    }

    /**
     * 单台矿机算力分析图
     * address 钱包地址
     * minner 矿机名称
     *
     * @return
     */
    @GetMapping("/banlanceshareByOne")
    public Resp balanceShares(String coin, String address, String minner) {
        String code = HttpStatus.OK.toString();
        String message = "请求成功";

        //  todo  根据address  获取子账户名称
        JSONObject jsonObject = accountAPI.getUsernameByCoinAndAddress(coin,address);

        Map<String, Object> resultMap = new HashMap<>();

        String accoutName = jsonObject.getString("data");
        if (jsonObject.getInt("state") == 200 && !accoutName.equals("null")) {
            //todo  通过子账户名去查找单台矿机的算力
            String timeStr = simpleDate.format(new Date());

            String key = coin.toUpperCase() + ":ACC:SHARE:" + accoutName + "." + minner;
            Set<String> shares = redisUtil.getSubKey(key + "*");
            List<String> redisKeyList = new ArrayList<>(shares);
            //通过key取值
            redisKeyList.forEach(v -> {
                //通过key 取值  ， 先比较一下是否是今天的日期
                if (StringUtils.substringAfterLast(v, ":").equals(timeStr)) {
                    Map<Object, Object> redisMap = redisUtil.hmget(v);
                    resultMap.put(v, redisMap);
                }
            });
        } else {
            code = HttpStatus.INTERNAL_SERVER_ERROR.toString();
            message = "程序出错";
        }
        return Resp.result(code, message, resultMap);
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
    @RequestMapping("/findDataByAddress")
    public Resp findDataByAddress(@RequestParam("address") String address, @RequestParam("coin") String coin) {

        JSONObject dataByAddress =  accountAPI.findDataByAddress(address, coin);
        Object data = dataByAddress.get("data");
        if(data!=null){
            System.out.println("跳转到登录页面");
            return Resp.success("已经注册的用户,请先登录");
        }else {
            Resp resp = forceAnalysis(address, coin);
            return resp;
        }
    }

}
