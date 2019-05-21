package com.xn.bmpos.manager.bmposmanager.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.netflix.discovery.converters.Auto;
import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinSetting;
import com.xn.bmpos.manager.bmposmanager.domain.model.FeignModel;
import com.xn.bmpos.manager.bmposmanager.feign.AccountAPI;
import com.xn.bmpos.manager.bmposmanager.service.CoinSettingService;
import com.xn.bmpos.manager.bmposmanager.util.Resp;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
@Slf4j
public class AccountCenterController extends BaskController {
    @Autowired
    AccountAPI accountAPI;

    @Autowired
    private CoinSettingService coinSettingService;

    @RequestMapping(value = "/account_add", method = RequestMethod.POST)
    public Resp add(@RequestParam("name") String name, @RequestParam("remarkName") String remarkName, @RequestParam(value = "password", required = false) String password, HttpSession session) {
        Integer userId = getUidfromSession(session);
        JSONObject add = accountAPI.add(name, remarkName, password, userId);
        FeignModel feignModel = new Gson().fromJson(add.toString(), FeignModel.class);
        return Resp.success(feignModel);
    }

    @RequestMapping(value = "/account_change", method = RequestMethod.POST)
    public Resp change(@RequestParam("remarkName") String name, @RequestParam(value = "password", required = false) String password, @RequestParam("id") Integer id, HttpSession session) {
        Integer userId = getUidfromSession(session);
        JSONObject change = accountAPI.change(name, password, id, userId);
        FeignModel feignModel = new Gson().fromJson(change.toString(), FeignModel.class);
        return Resp.success(feignModel);
    }

    @RequestMapping(value = "/account_get", method = RequestMethod.POST)
    public Resp getByUid(HttpSession session) {
        Integer userId = getUidfromSession(session);
        JSONObject byUid = accountAPI.getByUid(userId);
        FeignModel feignModel = new Gson().fromJson(byUid.toString(), FeignModel.class);
        System.err.println(byUid);
        return Resp.success(feignModel);
    }

    @RequestMapping(value = "/account_delete", method = RequestMethod.POST)
    public Resp dropByidAC(@RequestParam("id") Integer id, HttpSession session) {
        Integer userId = getUidfromSession(session);
        JSONObject jsonObject = accountAPI.dropByidAC(id, userId);
        int state = jsonObject.getInt("state");
        if(state==200){
            return Resp.success();
        }
        return new Resp("400",jsonObject.getString("message"));
    }


    @RequestMapping(value = "/account_get_account_name", method = RequestMethod.GET)
    public Resp selectAccountName(HttpSession session) {
        Integer userId = getUidfromSession(session);
        JSONObject jsonObject = accountAPI.selectAccountName(userId);
        FeignModel feignModel = new Gson().fromJson(jsonObject.toString(), FeignModel.class);
        //System.err.println(feignModel.getData());
        List<Object> objectList = new ArrayList<>();
        if(feignModel.getData() != null){
            JSONArray array = JSONArray.fromObject(feignModel.getData());
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                //System.err.println(obj);
                String coin = obj.getString("currency");
                if("无币种".equals(coin)){
                    obj.put("img","");
                }else {
                    CoinSetting setting = coinSettingService.selectCoin(coin);
                    if(setting==null){
                        obj.put("img","");
                    }else {
                        obj.put("img",setting.getImage());
                    }

                }
                objectList.add(obj);
            }
        }
       // System.err.println(objectList);
        return Resp.success(objectList);
    }
    //todo========================== address =================

    @RequestMapping(value = "/address_change", method = RequestMethod.POST)
    public Resp change(@RequestParam("id") Integer id, @RequestParam("coinAddress") String coinAddress, HttpSession session) {
        Integer userId = getUidfromSession(session);
        JSONObject jsonObject = accountAPI.changeAdress(id, coinAddress, userId);
        FeignModel feignModel = new Gson().fromJson(jsonObject.toString(), FeignModel.class);
        return Resp.success(feignModel);
    }

    @RequestMapping(value = "/address_get_aid", method = RequestMethod.GET)
    public Resp getByAccountId(@RequestParam("accountId") Integer accountId, HttpSession session) {
        Integer userId = getUidfromSession(session);
        JSONObject byAccountId = accountAPI.getByAccountId(accountId, userId);
        FeignModel feignModel = new Gson().fromJson(byAccountId.toString(), FeignModel.class);
        List<Object> objectList = new ArrayList<>();
        if (feignModel.getData() != null) {
            JSONArray array = JSONArray.fromObject(feignModel.getData());
            for (int i = 0; i < array.size(); i++) {
                JSONObject obj = (JSONObject) array.get(i);
                //System.err.println("obj:"+obj);
                String coin = obj.getString("currency");
                //System.err.println("coin:"+coin);
                CoinSetting setting = coinSettingService.selectCoin(coin);
                if(setting!=null){
                    if(setting.getName().equals(coin)){
                        obj.put("img",setting.getImage());
                    }
                }
                objectList.add(obj);
            }
        }
        return Resp.success(feignModel.getData());
    }

    @RequestMapping(value = "/address_drop", method = RequestMethod.POST)
    public Resp dropByid(@RequestParam("id") Integer id, HttpSession session) {
        Integer userId = getUidfromSession(session);
        JSONObject jsonObject = accountAPI.dropByid(id, userId);
        if(jsonObject.getInt("state")==200){
            return Resp.success();
        }
        return new Resp("400",jsonObject.getString("message"));
    }

    @RequestMapping(value = "/address_add", method = RequestMethod.POST)
    public Resp addAddress(@RequestParam("accountId") Integer accountId, @RequestParam("currency") String currency, @RequestParam("coinAddress") String coinAddress){
        JSONObject jsonObject = accountAPI.addAddress(accountId, currency, coinAddress);
        if(jsonObject.getInt("state")==200){
            return Resp.success();
        }
        return new Resp("400",jsonObject.getString("message"));
    }

}
