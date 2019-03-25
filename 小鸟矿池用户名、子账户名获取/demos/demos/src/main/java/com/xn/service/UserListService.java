package com.xn.service;

import com.xn.dao.UserListDao;
import com.xn.domain.Accounts;
import com.xn.domain.QueryResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class UserListService {
    @Autowired
    private UserListDao userListDao;

    public QueryResult findUserListApi(String last_id) {
        try {
            //创建map集合用来封装data数据
            Map<String, Integer> data = new LinkedHashMap<>();
            //如果为空，默认从1开始查询
            if (last_id == null || last_id.equals("")) {
                List<Accounts> accounts = userListDao.findUserList("1");
                bianli(accounts,data);
                return new QueryResult(0, null, data);
            }

            //如果传入参数，执行此方法
            List<Accounts> accounts = userListDao.findUserList(last_id);
            if (accounts.size() == 0) {
                return new QueryResult<ArrayList>(0, null, new ArrayList());
            } else {
                bianli(accounts,data);
                return new QueryResult(0, null, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //遍历数据，取出key,Value
    public void bianli(List<Accounts> accounts,Map<String, Integer> data ){
        for (int i = 0; i < accounts.size(); i++) {
            String key = accounts.get(i).getUsername();
            Integer value = accounts.get(i).getId();
            data.put(key, value);
        }
    }

}