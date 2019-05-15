package com.xn.bmpos.api.bmposapi.service.impl;

import com.xn.bmpos.api.bmposapi.common.http.HttpAPIService;
import com.xn.bmpos.api.bmposapi.feign.AccountAPI;
import com.xn.bmpos.api.bmposapi.service.MyMillService;
import com.xn.bmpos.api.bmposapi.tool.RedisUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MyMillServiceImpl implements MyMillService {
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AccountAPI accountAPI;
    @Autowired
    private HttpAPIService httpAPIService;
    private SimpleDateFormat simpleDateTime = new SimpleDateFormat("yyMMdd:HH");//转换年月日
    private SimpleDateFormat simpleDateTimeMim = new SimpleDateFormat("yyMMdd-HH:mm");//转换年月日
    public Map<String,Object> getInfo(String coin,String user){
        List<Map<String, Object>> oneMill = new ArrayList<>();
        List<Map<String, Object>> oneOffMill = new ArrayList<>();
        Map<String,Object> datas = new HashMap<>();
        JSONObject accountResult = accountAPI.getByAidAndCurrency(user,coin);
        System.err.println(accountResult);
        //todo 如果返回 不是 200 表示没有 用户
        if (accountResult.getInt("state") == 200) {
            System.out.println("查找到的数据");
            String coinAddress = accountResult.getJSONObject("data").getString("coinAddress");
            Set<Object> totaltSet = redisUtil.sGet(coin.toUpperCase()+":TOTAL:"+user);
            Set<Object> onlinetSet = redisUtil.sGet(coin.toUpperCase()+":ONLINE:"+user);
            Collection exists=new ArrayList(totaltSet);
            Collection notexists=new ArrayList(totaltSet);
            notexists.removeAll(onlinetSet);
            exists.removeAll(notexists);
            //System.err.println("exists"+exists);
            //System.err.println("notexists"+notexists);
            if(!exists.isEmpty()||exists!=null){
                Map<Object,Object> userMap = redisUtil.hmget(coin.toUpperCase()+":ACCOUNT:STATE:"+user);
                Date now = new Date();
                String timeStr = simpleDateTime.format(now);
                String[] arrStr = timeStr.split(":");
                Object userHashrate = redisUtil.hget(coin.toUpperCase()+":ACC:SHIS:"+user+":REAL:"+arrStr[0],arrStr[1]);

                Object blanace = userMap.get("blanace");
                Object unpaid = userMap.get("unpaid");
                Object totalpaid = userMap.get("total");
                Double sumDifficulty24 = Double.valueOf(userMap.get("sumDifficulty24").toString());
                Double avgDifficulty24 = sumDifficulty24/24;
                Map<Object,Object> vcointMap = redisUtil.hmget(coin.toUpperCase()+":ACCOUNT:VCOUNT:"+user);
                Map<Object,Object> uncointMap = redisUtil.hmget(coin.toUpperCase()+":ACCOUNT:UNCOUNT:"+user);
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
                //循环在线矿机
                for(Object on:exists){
                    String name = on.toString();
                    String mill = name.substring(name.lastIndexOf(".")+1);
                    JSONObject newNameJson = accountAPI.getNameByOldName(mill, coin);
                    if(newNameJson.size()>1&&newNameJson.getInt("state")==200){
                        JSONObject data = newNameJson.getJSONObject("data");
                        Integer groupId = data.getInt("groupId");
                        String groupName = data.getString("groupName");
                        String newName = data.getString("millName");
                        Integer millId = data.getInt("millId");
                        Map<String, Object> oneMillInfo = getOneMillInfo(coin,name);
                        oneMillInfo.put("millId",millId);
                        oneMillInfo.put("groupId",groupId);
                        oneMillInfo.put("groupName",groupName);
                        oneMillInfo.put("newName",newName);
                        oneMill.add(oneMillInfo);
                    }


                }
                //循环离线
                for(Object off:notexists){
                    System.err.println(off);
                    String name = off.toString();
                    String mill = name.substring(name.lastIndexOf(".")+1);
                    JSONObject newNameJson = accountAPI.getNameByOldName(mill, coin);
                    if(newNameJson.size()>1&&newNameJson.getInt("state")==200){
                        JSONObject data = newNameJson.getJSONObject("data");
                        Integer groupId = data.getInt("groupId");
                        String groupName = data.getString("groupName");
                        String newName = data.getString("millName");
                        Integer millId = data.getInt("millId");
                        Map<String, Object> oneMillInfo = getOneMillInfo(coin,name);
                        oneMillInfo.put("groupId",groupId);
                        oneMillInfo.put("groupName",groupName);
                        oneMillInfo.put("newName",newName);
                        oneMillInfo.put("millName",name);
                        oneMillInfo.put("millId",millId);
                        // todo 测试数据实际删除
                        oneMillInfo.put("timeobj","19-05-11 04:44");
                        oneOffMill.add(oneMillInfo);
                    }
                }
                datas.put("vcoint24",vcoint24);
                datas.put("uncoint24",uncoint24);
                if(userHashrate!=null){
                    datas.put("userHashrate",userHashrate);
                }else {
                    datas.put("userHashrate",0.0);
                }

                datas.put("address",coinAddress);
                Integer total = totaltSet.size();
                datas.put("total",total);
                Integer online = onlinetSet.size();
                datas.put("online",online);
                Integer offline = total-online;
                datas.put("offline",offline);
                datas.put("blanace",blanace);
                datas.put("unpaid",unpaid);
                datas.put("totalpaid",totalpaid);
                datas.put("avgDifficulty24",avgDifficulty24);
                datas.put("offMill",oneOffMill);//离线
                datas.put("onMill",oneMill);//在线
            }

        }

        return datas;
    }

    @Override
    public Map<String, Object> getOneMillInfo(String coin, String millName) {
        Date now = new Date();
        String nowTime = simpleDateTimeMim.format(now);
        String[] subTime = nowTime.split("-");
        String date = subTime[0];
        String time = subTime[1];
        String[] timestr = time.split(":");
        Integer min = Integer.valueOf(timestr[1]);
        if(min>=45){
            time = timestr[0]+":45";
        }else if(min>=30){
            time = timestr[0]+":30";
        }else if(min>=15){
            time = timestr[0]+":15";
        }else {
            time = timestr[0]+":00";
        }
        //获取实时算力
        Object realHashrat = redisUtil.hget(coin.toUpperCase() + ":ACC:SHARE:" + millName + ":REAL:" + date, time);

        Map<String, Object> data = new HashMap<>();
         Map<Object,Object> reslut = redisUtil.hmget(coin.toUpperCase()+":MILL:STATE:"+millName);
         Object sumDifficulty15Obj = reslut.get("sumDifficulty15");
         Object valid15obj = reslut.get("valid15");
        Object timeobj = reslut.get("time");
        double difficulty15 = 0.0;
        if(sumDifficulty15Obj!=null){
            difficulty15 = Double.valueOf(sumDifficulty15Obj.toString());
        }
        double valid15 = 0.0;
        if(valid15obj!=null){
            valid15 = Double.valueOf(valid15obj.toString());
        }
         Map<Object,Object> reslut1 = redisUtil.hmget(coin.toUpperCase()+":MILL:ONEHOUR:"+millName);
         double difficulty24 = 0;
        for (Object value : reslut1.values()) {
            double difficulty = Double.valueOf(value.toString());
            difficulty24 += difficulty;
        }
        int vcount = 0;
        Map<Object,Object> vcountObj = redisUtil.hmget(coin.toUpperCase()+":MILL:VCOUNT:"+millName);
        for (Object value : vcountObj.values()) {
            int valueInt = Integer.valueOf(value.toString());
            vcount += valueInt;
        }
        int uncount = 0;
        Map<Object,Object> uncountObj = redisUtil.hmget(coin.toUpperCase()+":MILL:UNCOUNT:"+millName);
        for (Object value : uncountObj.values()) {
            int unvalueInt = Integer.valueOf(value.toString());
            uncount += unvalueInt;
        }
        data.put("millName",millName);
        data.put("difficulty15",difficulty15);
        data.put("difficulty24",difficulty24);
        if(realHashrat != null){
            data.put("realHashrate",realHashrat);
        }else {
            data.put("realHashrate",0);
        }
        data.put("valid15",valid15);
        data.put("vcount",vcount);
        data.put("uncount",uncount);
        data.put("timeobj",timeobj);
        return data;
    }
}
