package com.xn.bmpos.api.bmposapi;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.netflix.discovery.converters.Auto;
import com.xn.bmpos.api.bmposapi.common.http.HttpAPIService;
import com.xn.bmpos.api.bmposapi.domain.model.Record;
import com.xn.bmpos.api.bmposapi.service.RecordService;
import com.xn.bmpos.api.bmposapi.tool.RedisUtil;
import com.xn.bmpos.api.bmposapi.tool.Resp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.ListOrderedMap;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BmposApiApplicationTests {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private HttpAPIService httpAPIService;

    @Autowired
    private RecordService recordService;

    private SimpleDateFormat simpleDate = new SimpleDateFormat("yyMMdd");

    @Test
    public void contextLoads() {
        String coin = "ltc";
        String accoutName = "LTC";
        String minner = "19x10";
        String timeStr = simpleDate.format(new Date());
        String key = coin.toUpperCase() + ":ACC:SHARE:" + accoutName + "." + minner;
        Set<String> shares = redisUtil.getSubKey(key + "*");
        List<String> redisKeyList = new ArrayList<>(shares);
        //通过key取值
        redisKeyList.forEach(v -> {
            System.out.println(v);
            Map<Object, Object> resultMap = redisUtil.hmget(v);
            System.out.println(resultMap);
        });

    }

}
