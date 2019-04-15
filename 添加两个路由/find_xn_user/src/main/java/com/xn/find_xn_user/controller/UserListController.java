package com.xn.find_xn_user.controller;

import com.xn.find_xn_user.domain.QueryResult;
import com.xn.find_xn_user.domain.RegisterResult;
import com.xn.find_xn_user.domain.UserIDResult;
import com.xn.find_xn_user.service.UserListService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class UserListController {
    @Autowired
    private UserListService userListService;

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
    @RequestMapping(value = "/pool/userid/username", method = RequestMethod.GET)
    public UserIDResult findUsernameById(String username) {
        return userListService.findUsernameById(username);
    }


    /**
     * 根据传入的用户名和密码入库
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping(value = "/pool/users/id", method = RequestMethod.GET)
    public RegisterResult saveUser(@RequestParam(value = "username", required = false) String username, @RequestParam("password") String password) {
        RegisterResult registerResult = new RegisterResult();
        registerResult.setUsername(username);
        registerResult.setpassword(password);
        return userListService.saveUser(registerResult);
    }
}

