package com.xn.bmpos.manager.bmposmanager.feign;

import net.sf.json.JSONObject;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Component
@FeignClient(name = "account-api")
public interface AccountAPI {
    //todo==================account 内部用 ===================
    @RequestMapping(value = "/address/get_ac", method = RequestMethod.POST)
    JSONObject getByAidAndCurrency(@RequestParam("name") String name, @RequestParam("coin") String coin);

    @RequestMapping(value = "/account/get_user", method = RequestMethod.GET)
    JSONObject getUsernameByCoinAndAddress(@RequestParam("coin") String coin, @RequestParam("address") String address);

    @RequestMapping(value = "/account/findUsersAndCoins", method = RequestMethod.GET)
    JSONObject findUsersAndCoins(@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/account/findDataByAddress", method = RequestMethod.GET)
    JSONObject findDataByAddress(@RequestParam("address") String address, @RequestParam("coin") String coin);

    @RequestMapping(value = "/account/finduserIdByUsersName", method = RequestMethod.GET)
    JSONObject finduserIdByUsersName(@RequestParam("usersName") String usersName);

    //todo==================account 对外用 ====================

    @RequestMapping(value = "/account/add", method = RequestMethod.POST)
    JSONObject add(@RequestParam("name") String name,@RequestParam("remarkName") String remarkName,@RequestParam(value = "password",required=false) String password ,@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/account/change", method = RequestMethod.POST)
    JSONObject change(@RequestParam(value = "remarkName",required=false) String remarkName,@RequestParam(value = "password",required=false ) String password,@RequestParam("id") Integer id,@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/account/get", method = RequestMethod.POST)
    JSONObject getByUid(@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/account/delete", method = RequestMethod.POST)
    JSONObject dropByidAC(@RequestParam("id") Integer id,@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/account/get_account_name", method = RequestMethod.GET)
    JSONObject selectAccountName(@RequestParam("userId") Integer userId);

    //todo========================== address =================

    @RequestMapping(value = "/address/change", method = RequestMethod.POST)
    JSONObject changeAdress(@RequestParam("id") Integer id, @RequestParam("coinAddress") String coinAddress,@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/address/get_aid", method = RequestMethod.GET)
    JSONObject getByAccountId(@RequestParam("accountId") Integer accountId,@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/address/drop", method = RequestMethod.POST)
    JSONObject dropByid(@RequestParam("id") Integer id,@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/address/add", method = RequestMethod.POST)
    JSONObject addAddress(@RequestParam("accountId") Integer accountId, @RequestParam("currency") String currency, @RequestParam("coinAddress") String coinAddress);

    //todo========================== mill====================

    @RequestMapping(value = "/mill/get_name", method = RequestMethod.GET)
    JSONObject getNameByOldName(@RequestParam("oldName") String oldName, @RequestParam("coin") String coin);

    @RequestMapping(value = "/mill/change", method = RequestMethod.GET)
    JSONObject changeName(@RequestParam("name") String name,@RequestParam("oldName") String oldName, @RequestParam("accountName") String accountName);

    @RequestMapping(value = "/mill/delGroup", method = RequestMethod.POST, produces = "application/json")
    JSONObject delGroup(@RequestBody List<String> groupIds,@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/mill/group", method = RequestMethod.POST, produces = "application/json")
    JSONObject group(@RequestParam("accountName") String accountName, @RequestParam("groupId") Integer groupId, @RequestBody List<String> indexs,@RequestParam("userId") Integer userId);

    @RequestMapping(value = "/mill/addGroup", method = RequestMethod.GET)
    JSONObject addGroup(@RequestParam("groupName") String groupName, @RequestParam("userId") Integer userId);

    //todo========================== 用户登录 api =============
    @RequestMapping(value = "/api/login", method = RequestMethod.POST)
    JSONObject getLogin(@RequestParam(value = "username") String username, @RequestParam(value = "password") String password,
                        @RequestParam(value = "place") String place);

    @RequestMapping(value = "/api/social", method = RequestMethod.GET)
    JSONObject social(@RequestParam(value = "type") String type, @RequestParam(value = "openId") String openId);

    @RequestMapping(value = "/api/regist", method = RequestMethod.POST)
    JSONObject registUser(@RequestParam(value ="username") String username, @RequestParam(value ="phone")String phone,
                          @RequestParam(value ="password")String password);

    @RequestMapping(value = "/api/tokenVerify", method = RequestMethod.GET)
    JSONObject tokenVerify(@RequestParam(value ="token")String token);


    @RequestMapping(value = "/api/updateUser", method = RequestMethod.POST)
    JSONObject updateUserByEmail(@RequestParam(value ="username")String username,@RequestParam(value ="email") String email);

    @RequestMapping(value = "/api/updateUser", method = RequestMethod.POST)
    JSONObject updateUserPhone(@RequestParam(value ="username")String username,@RequestParam(value ="phone") String phone);

    @RequestMapping(value = "/api/updateUser", method = RequestMethod.POST)
    JSONObject updateUserPassword(@RequestParam(value ="username")String username, @RequestParam(value ="pwd")String pwd);

    @RequestMapping(value = "/api/updateUser", method = RequestMethod.POST)
    JSONObject updateUserStreet(@RequestParam(value ="username")String username, @RequestParam(value ="street")String street);

    @RequestMapping(value = "/api/findLogin", method = RequestMethod.GET)
    JSONObject findLogin(@RequestParam(value ="token")String token);

    @RequestMapping(value = "/api/sendEmail", method = RequestMethod.GET)
    JSONObject sendEmail(@RequestParam(value = "emailVerifi") String email);

    @RequestMapping(value = "/api/emailVerifi", method = RequestMethod.GET)
    JSONObject emailVerifi(@RequestParam(value = "emailVerifi")String emailVerifi,@RequestParam(value = "code")String code);
}
