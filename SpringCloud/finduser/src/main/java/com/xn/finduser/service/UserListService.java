package com.xn.finduser.service;

import com.xn.finduser.dao.UserListDao;
import com.xn.finduser.domain.Accounts;
import com.xn.finduser.domain.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserListService {
    @Autowired
    private UserListDao userListDao;

    public List<Accounts> findUserListApi() {
        System.out.println("业务层");
        List<Accounts> accounts = userListDao.findUserList();
        return accounts;
    }

}