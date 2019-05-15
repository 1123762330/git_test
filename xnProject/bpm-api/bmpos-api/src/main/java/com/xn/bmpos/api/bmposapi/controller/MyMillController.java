package com.xn.bmpos.api.bmposapi.controller;

import com.xn.bmpos.api.bmposapi.domain.dao.MillInfoGroup;
import com.xn.bmpos.api.bmposapi.feign.AccountAPI;
import com.xn.bmpos.api.bmposapi.service.MyMillService;
import com.xn.bmpos.api.bmposapi.tool.Resp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 我的矿机
 */
@RestController
@RequestMapping("api")
public class MyMillController {
    @Autowired
    private MyMillService myMillService;
    @Autowired
    AccountAPI accountAPI;

    @GetMapping("/get_info")
   public Resp getInfo(@RequestParam("coin") String coin, @RequestParam("user") String user){
       Map<String,Object> datas = myMillService.getInfo(coin,user);
       return Resp.success(datas);
    }


    /**
     * 单台矿机信息
     * @param coin
     * @param millName
     * @return
     */
    @GetMapping("/get_one")
   public Resp getOneMillInfo(String coin,String millName){
       Map<String, Object> oneMillInfo = myMillService.getOneMillInfo(coin, millName);
       return Resp.success(oneMillInfo);
   }


    @GetMapping(value = "/get_group")
    public Resp getGroup(String usersName,String coin){
        JSONObject jsonObject = accountAPI.selectGroup(usersName, coin);

        ArrayList<Object> list = new ArrayList<>();
        JSONObject millGroup = null;
        if(jsonObject.getInt("state")==200&&jsonObject.size()>1){
            Object data = jsonObject.get("data");
            JSONArray jsonArray=JSONArray.fromObject(data);
            Map<String, Object> oneMillInfo=new HashMap<>();

            for(int i=0;i<jsonArray.size();i++){
                Object object = jsonArray.get(i);
                millGroup = JSONObject.fromObject(object);
                System.out.println("==="+millGroup);
                String accountName = millGroup.getString("accountName");
                String oldName = millGroup.getString("oldName");
                String millName = accountName+"."+oldName;
                System.err.println("millName:"+millName);
                oneMillInfo = myMillService.getOneMillInfo(coin, millName);
                System.err.println("单台矿机的数据:"+oneMillInfo);
                millGroup.put("millInfo",oneMillInfo);
                System.out.println("新的数据"+millGroup);
                MillInfoGroup bean = (MillInfoGroup) JSONObject.toBean(millGroup, MillInfoGroup.class);
                System.out.println("=============="+bean.toString());
                Map millInfo = bean.getMillInfo();
                Object timeobj = millInfo.get("timeobj");
                //todo 测试用时间没有空数据
                if(timeobj==null||"null".equals(timeobj)||timeobj=="null"){
                    System.err.println("   444:"+timeobj);
                }
                millInfo.replace("timeobj","19-05-11 04:44");

                list.add(bean);

            }

        }
        System.out.println("集合:"+list);
        //MillInfoGroup bean = (MillInfoGroup) JSONObject.toBean(millGroup,MillInfoGroup.class);
        return Resp.success(list);
    }
}
