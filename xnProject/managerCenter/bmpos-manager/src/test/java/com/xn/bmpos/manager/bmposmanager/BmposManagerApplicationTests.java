package com.xn.bmpos.manager.bmposmanager;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.plugins.pagination.PageHelper;
import com.xn.bmpos.manager.bmposmanager.domain.model.IndexCoinModel;
import com.xn.bmpos.manager.bmposmanager.util.OkHttpUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BmposManagerApplicationTests {

    @Test
    public void contextLoads() {
        String ulr = "http://192.168.0.44:8982/api/get_pool_hashrate";
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", "ltc");
        String str = OkHttpUtils.get(ulr, getMap);
        JSONObject jsonObject = JSONObject.parseObject(str);
        Map<String, Object> itemMap = JSONObject.toJavaObject(jsonObject.getJSONObject("data"), Map.class);


        System.out.println(itemMap);
    }

}
