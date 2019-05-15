package com.spring.xnpool.bmpos.tasks.bmpostasks;

import com.alibaba.fastjson.JSON;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.daily.DailyVO;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ConfigMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.TransacTionsMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.HttpResuletModel;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl.HttpAPIService;
import net.sf.json.JSONObject;
import org.junit.Test;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BmposTasksApplicationTests {
    @Autowired
    private HttpAPIService httpAPIService;
    @Autowired
    private ConfigMapper configMapper;

    private static String wallentIp;

    @Value("${httpClient.wallet-ip}")
    public void getWallentIp(String wallentIps) {
        wallentIp = wallentIps;
    }

    @Autowired
    private TransacTionsMapper transacTionsMapper;

    private static String accountIp;

    @Value("${httpClient.account-ip}")
    public void getaccountIp(String accountIps) {
        accountIp = accountIps;
    }

    @Test
    public void Test() throws Exception {
        Map<String, Object> map = new HashMap();
        map.put("coin", "ltc");
        map.put("name", "333");

        JSONObject accountResult = httpAPIService.doPost(accountIp + "/address/get_ac", map);

        int state = accountResult.getInt("state");
        System.out.println(accountResult);
    }

    @Test
    public  void testThred() {


    }

    public synchronized void run(){
        for (int i=0;i<1000;i++){
            System.out.print("第" + i);
        }
    }

}
