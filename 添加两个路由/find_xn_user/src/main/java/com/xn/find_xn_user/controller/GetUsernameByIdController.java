package com.xn.find_xn_user.controller;

import com.google.gson.Gson;
import com.xn.find_xn_user.domain.QueryResult;
import com.xn.find_xn_user.domain.UserIDResult;
import com.xn.find_xn_user.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
public class GetUsernameByIdController {
    @Autowired
    private UserListService userListService;
    Gson gson = new Gson();

    /**
     * 根据用户id进行分条查询
     *
     * @param last_id 指定用户id
     * @return
     */
    @RequestMapping(value = "userlist-api", method = RequestMethod.GET)
    public QueryResult userlistapi(@RequestParam(value = "last_id", required = false) String last_id) {
        return userListService.findUserListApi(last_id);
    }

    /**
     * 根据用户名查询ID
     *
     * @param username 传入的用户名
     * @return 返回状态值
     */
    @RequestMapping(value = "/pool/userid/{username}", method = RequestMethod.GET)
    public UserIDResult findUsernameById(@PathVariable("username") String username) {
        return userListService.findUsernameById(username);
    }

    //如果用户没有输入用户名就给与提示
    @RequestMapping(value = "/pool/userid/", method = RequestMethod.GET)
    public String findUsernameById() {
        UserIDResult userIDResult = new UserIDResult("没有输入用户名,url格式不正确!", 500);
        String json = gson.toJson(userIDResult);
        return  json;
    }
}
