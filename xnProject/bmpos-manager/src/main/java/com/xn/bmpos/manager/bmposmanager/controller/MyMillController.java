package com.xn.bmpos.manager.bmposmanager.controller;

import com.google.gson.Gson;
import com.xn.bmpos.manager.bmposmanager.domain.model.FeignModel;
import com.xn.bmpos.manager.bmposmanager.domain.model.RewardModel;
import com.xn.bmpos.manager.bmposmanager.exception.ErrorCode;
import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import com.xn.bmpos.manager.bmposmanager.util.GetData;
import com.xn.bmpos.manager.bmposmanager.util.OkHttpUtils;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
@RequestMapping("api")
@Slf4j
public class MyMillController extends BaskController {

    @Autowired
    private GetData getData;
    @Autowired
    private AccountAPI accountAPI;

    /**
     *
     * @param coin
     * @param user 子账户
     * @return
     */
    @PostMapping(value = "/get_info")
    public Resp forceAnalysis (String coin, String user) {
        if (StringUtils.isEmpty(user) || StringUtils.isEmpty(coin)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("user", user);

        Object data = getData.postDataToObject(coin,getMap);
        if(data==null){
            return new Resp("400","查询返回的数据为null");
        }
        FeignModel feignModel = new Gson().fromJson(data.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * 获取分组矿机
     * @param coin
     * @return
     */
    @PostMapping(value = "/get_group")
    public Resp getGroup(HttpSession session,String coin){
        Integer userId = getUidfromSession(session);
        if (StringUtils.isEmpty(coin)) {
            return Resp.fail(ErrorCode.PARAMETER_CHECK_ERROR);
        }
        Map<String, String> getMap = new HashMap<>();
        getMap.put("coin", coin);
        getMap.put("userId", userId.toString());


        Object data = getData.postDataToObject(coin,getMap);
        if(data==null){
            return new Resp("400","查询返回的数据为null");
        }
        FeignModel feignModel = new Gson().fromJson(data.toString(), FeignModel.class);
        return Resp.success(feignModel.getData());
    }

    /**
     * 删除矿机
     * @param millIds
     * @return
     */
    @RequestMapping(value = "/del_mill", method = RequestMethod.POST)
    public Resp delMill(String millIds,HttpSession session) {
        Integer userId = getUidfromSession(session);
        String[] strings = millIds.split(",");
        List<String> list = Arrays.asList(strings);
        JSONObject jsonObject = accountAPI.delGroup(list,userId);
        FeignModel feignModel = new Gson().fromJson(jsonObject.toString(), FeignModel.class);
        return Resp.success(feignModel);
    }

    /**
     * 移动矿机
     * @param accountName
     * @param groupId
     * @param
     * @return
     */
    @RequestMapping(value = "/group", method = RequestMethod.POST)
    public Resp group(String accountName, Integer groupId,String indexs,HttpSession session){
        Integer userId = getUidfromSession(session);
        String[] strings = indexs.split(",");
        List<String> list = Arrays.asList(strings);
        JSONObject group = accountAPI.group(accountName, groupId, list,userId);
        FeignModel feignModel = new Gson().fromJson(group.toString(), FeignModel.class);
        return Resp.success(feignModel);
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
        FeignModel feignModel = new Gson().fromJson(jsonObject.toString(), FeignModel.class);
        return Resp.success(feignModel);
    }

    /**
     * 添加分组
     * @param groupName
     * @param session
     * @return
     */
    @RequestMapping(value = "/mill_addGroup", method = RequestMethod.GET)
    public Resp addGroup(@RequestParam("groupName") String groupName, HttpSession session){
        Integer userId = getUidfromSession(session);
        JSONObject jsonObject = accountAPI.addGroup(groupName, userId);
        FeignModel feignModel = new Gson().fromJson(jsonObject.toString(), FeignModel.class);
        return Resp.success(feignModel);
    }

}
