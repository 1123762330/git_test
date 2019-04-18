package com.xn.find_xn_user.service;

import com.xn.find_xn_user.domain.*;

import java.util.List;

public interface UserListService {
    QueryResult findUserListApi(String last_id);

    UserIDResult findUsernameById(String username);

    List findAllUser();

    List findAllPoolWorker();

    Accounts selectUser(String username);
}
