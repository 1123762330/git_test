package com.xn.find_xn_user.service.impl;

import com.xn.find_xn_user.dao.UserListDao;
import com.xn.find_xn_user.domain.Accounts;
import com.xn.find_xn_user.domain.QueryResult;
import com.xn.find_xn_user.domain.RegisterResult;
import com.xn.find_xn_user.domain.UserIDResult;
import com.xn.find_xn_user.service.UserListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserListServiceImpl implements UserListService {
    @Autowired
    private UserListDao userListDao;

    @Override
    public QueryResult findUserListApi(String last_id) {
        try {
            //创建map集合用来封装data数据
            Map<String, Integer> data = new LinkedHashMap<>();
            //如果为空，默认从1开始查询
            if (last_id == null || last_id.equals("")) {
                List<Accounts> accounts = userListDao.findUserList("1");
                bianli(accounts, data);
                return new QueryResult(0, null, data);
            }

            //如果传入参数，执行此方法
            List<Accounts> accounts = userListDao.findUserList(last_id);
            if (accounts.size() == 0) {
                return new QueryResult<ArrayList>(0, null, new ArrayList());
            } else {
                bianli(accounts, data);
                return new QueryResult(0, null, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 遍历用户
     *
     * @param accounts 用户列表集合
     * @param data     封装的数据
     */
    public void bianli(List<Accounts> accounts, Map<String, Integer> data) {
        for (int i = 0; i < accounts.size(); i++) {
            String key = accounts.get(i).getUsername();
            Integer value = accounts.get(i).getId();
            data.put(key, value);
        }
    }

    /**
     * 根据用户名查询ID
     *
     * @param username
     * @return
     */
    @Override
    public UserIDResult findUsernameById(String username) {
        List allUser = findAllUser();
        if (allUser.contains(username)) {
            Integer usernameById = userListDao.findUsernameById(username);
            return new UserIDResult(usernameById, 200);
        } else if (!allUser.contains(username)) {
            Integer id = null;
            return new UserIDResult(id, 404);
        } else {
            return new UserIDResult("服务出现异常!!!", 500);
        }
    }

    @Override
    public List findAllUser() {
        List allUser = userListDao.findAllUser();
        return allUser;
    }

    @Override
    public RegisterResult saveUser(RegisterResult registerResult) {
        //查找数据库所有的用户
        List allUser = findAllUser();
        //将传入的用户同数据库中做对比,如果数据库没有就执行入库,返回相应的状态
        String username = registerResult.getUsername();
        if (!"".equals(username) && username != null && !"null".equals(username) && !allUser.contains(username)) {
            userListDao.saveUser(registerResult);
            return new RegisterResult(registerResult.getUsername(), registerResult.getId(), 201);
        } else {
            //否则就返回400状态
            return new RegisterResult("用户名重复或者用户名不符合规范", 400);
        }
    }


}