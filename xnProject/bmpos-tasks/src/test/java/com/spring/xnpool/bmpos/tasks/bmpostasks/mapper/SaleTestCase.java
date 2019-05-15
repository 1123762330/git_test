package com.spring.xnpool.bmpos.tasks.bmpostasks.mapper;


import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ConfigMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.ShareService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.SummarizedData;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl.HttpAPIService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.SimpleDateFormat;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SaleTestCase {
    private static String accountIp;

    @Value("${httpClient.account-ip}")
    public void getaccountIp(String accountIps) {
        accountIp = accountIps;
    }
    @Value("${httpClient.wallet-ip}")
    private static String wallentIp;
    @Autowired
    ShareService shareService;
    @Autowired
    SummarizedData summarizedData;
    @Autowired
    HttpAPIService httpAPIService;
    @Autowired
    RedisUtil redisUtil;
    @Autowired
    private ConfigMapper configMapper;

    private Map<String,Object> coinMap = new HashMap<>();
    SimpleDateFormat simpleDateTime = new SimpleDateFormat("HH:mm:ss");//转换年月日
    @Test
    public void test() throws Exception {
      summarizedData.sumDate("ltc");
        /* Set<String> redisUtilSubKey = redisUtil.getSubKey("LTC:ACCOUNT:SHARE:*");
         System.err.println(redisUtilSubKey.size());
         System.err.println(redisUtilSubKey);*/

         //boolean reslult = redisUtil.hasKey("LTC:ACCOUNT:STATE:sunh380");
        // System.err.println(reslult);
        //redisUtil.set("test1111","3edgedsffg");
       /* Map<String,Object> map = new HashMap<>();
        map.put("name","zhangfan");
        map.put("coin","ltc");
        JSONObject accountResult = httpAPIService.doPost(accountIp + "/address/get_ac", map);
        System.err.println(accountResult);
        Object data =  accountResult.get("data");
        JSONArray jsonArray = JSONArray.fromObject(data.toString());
        for (int i = 0; i < jsonArray.size(); i++) {
            System.err.println(jsonArray.get(i));
            JSONObject jsonObject = JSONObject.fromObject(jsonArray.get(i));
            String coinAddress = jsonObject.getString("coinAddress");
            System.err.println(coinAddress);
        }*/
        //List<SaleAccountVO> worker = json2.toJavaObject(SaleAccountVO.class);
       /* String user = "bmz8.0x129";
        String name = user.substring(0,user.lastIndexOf("."));
        System.err.println(name);*/

         //
      /*   Date time = simpleDateTime.parse("2019-04-26 00:00:36");
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        System.err.println("hour:"+hour);
        calendar.set(Calendar.HOUR_OF_DAY,hour);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        System.out.println("开始时间："+calendar.getTime());
       // dataMapOneHour.put("startTime",calendar.getTime());
        //calendar.set(calendar.D);
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MILLISECOND,999);
        System.out.println("结束时间："+calendar.getTime());*/
        //dataMapOneHour.put("endTime",calendar.getTime());

}
}
