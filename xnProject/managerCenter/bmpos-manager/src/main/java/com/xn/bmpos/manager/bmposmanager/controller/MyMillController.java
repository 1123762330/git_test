package com.xn.bmpos.manager.bmposmanager.controller;

import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import com.xn.bmpos.manager.bmposmanager.util.GetData;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api")
public class MyMillController {

    @Autowired
    private GetData getData;
    @Autowired
    private AccountAPI accountAPI;

    /**
     * 页面初始化信息
     *
     * @return
     */
    @GetMapping(value = "/get_info")
    public Resp forceAnalysis (String coin, String user) {
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("user", user);

        Object data = getData.getDataToObject(coin,getMap);
        return Resp.success(data);
    }

    /**
     * 获取分组矿机
     * @param usersName
     * @param coin
     * @return
     */
    @GetMapping(value = "/get_group")
    public Resp getGroup(String usersName,String coin){
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("usersName", usersName);

        Object data = getData.getDataToObject(coin,getMap);
        return Resp.success(data);
    }

    /**
     * 删除矿机
     * @param millIds
     * @return
     */
    @RequestMapping(value = "/del_mill", method = RequestMethod.POST, produces = "application/json")
    public Resp delMill(@RequestBody List<Integer> millIds) {
        JSONObject jsonObject = accountAPI.delGroup(millIds);
        if(jsonObject.getInt("state")==200){
            return Resp.success();
        }
       return Resp.fail();
    }

    /**
     * 移动矿机
     * @param accountName
     * @param groupId
     * @param indexs
     * @return
     */
    @RequestMapping(value = "/group", method = RequestMethod.POST, produces = "application/json")
    public Resp group(String accountName, Integer groupId, @RequestBody List<Integer> indexs){
        JSONObject group = accountAPI.group(accountName, groupId, indexs);
        if(group.getInt("state")==200){
            return Resp.success();
        }
        return Resp.fail();
    }

    /**
     * 更改矿机
     * @param name
     * @param oldName
     * @param accountName
     * @return
     */
    @RequestMapping(value = "/change", method = RequestMethod.GET)
    public Resp changeName(@RequestParam("name") String name,@RequestParam("oldName") String oldName, @RequestParam("accountName") String accountName){
        JSONObject jsonObject = accountAPI.changeName(name, oldName, accountName);
        if(jsonObject.getInt("state")==200){
            return Resp.success();
        }
        return Resp.fail();
    }


}
