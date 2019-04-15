package com.xn.find_xn_user.service;

import com.xn.find_xn_user.domain.QueryResult;
import com.xn.find_xn_user.domain.UserIDResult;
import com.xn.find_xn_user.domain.RegisterResult;

import java.util.List;

public interface UserListService {
    QueryResult findUserListApi(String last_id);

    UserIDResult findUsernameById(String username);

    List findAllUser();

    RegisterResult saveUser(RegisterResult registerResult);
}
