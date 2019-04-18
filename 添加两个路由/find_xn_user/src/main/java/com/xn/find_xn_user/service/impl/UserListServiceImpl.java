package com.xn.find_xn_user.service.impl;

import com.xn.find_xn_user.dao.UserListDao;
import com.xn.find_xn_user.domain.*;
import com.xn.find_xn_user.service.UserListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserListServiceImpl implements UserListService {
    public final Logger logger = LoggerFactory.getLogger(UserListServiceImpl.class);

    @Autowired
    private UserListDao userListDao;

    /**
     * 通过last_id进行条数查询
     *
     * @param last_id
     * @return
     */
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
     * 如果矿机名称不存在就入库
     *
     * @param username
     * @return
     */
    @Override
    public UserIDResult findUsernameById(String username) {
        String userStr = "";
        //查找出数据库的所有用户
        List allUser = findAllUser();
        List allPoolWorker = findAllPoolWorker();

        //对传入的参数进行切割,取出.前面的字母
        String[] user = username.split("\\.");
        for (int i = 0; i < user.length; i++) {
            userStr = user[0];
        }


        //对传入的用户名进行正则匹配,符合规则的进行入库操作
        boolean matches = username.matches("(^\\w*\\d*)*(\\.{1}\\d*)+x{1}\\d+$");
        //如果传入的用户名不存在就加入矿机表
        Integer accountId = null;
        if (!allPoolWorker.contains(username) && matches == true) {
            try {
                logger.info("矿机名称不存在!");
                //首先还要去获取account_id,去判断有没有这个用户,有才返回ID
                //然后通过username和AccountId进行入库
                if (allUser.contains(userStr)) {
                    accountId = userListDao.findUsernameById(userStr);//查询poolworker需要的account_id
                    userListDao.savePoolWorker(accountId, username);
                    logger.info("矿机名入库成功");
                } else {
                    logger.info("用户名不存在,或者用户名格式不正确!");
                }
            } catch (Exception e) {
                logger.info(e.getMessage());
            }
        }

        //如果存在就直接返回用户id
        //如果用户名字符串在用户名集合中,那么就根据用户名查ID
        if (allUser.contains(userStr)) {
            accountId = userListDao.findUsernameById(userStr);
            return new UserIDResult(accountId, 200);
        } else if (!allUser.contains(userStr)) {
            Integer id = null;
            return new UserIDResult(id, 404);
        } else {
            return new UserIDResult("服务出现异常!!!", 500);
        }

    }


    /**
     * 查找所有的矿机列表
     *
     * @return
     */
    @Override
    public List findAllPoolWorker() {
        List allPoolWorker = userListDao.findAllPoolWorker();
        return allPoolWorker;
    }

    @Override
    public Accounts selectUser(String username) {
        return  userListDao.selectUser(username);
    }

    /**
     * 查找所有的用户
     *
     * @return
     */
    @Override
    public List findAllUser() {
        List allUser = userListDao.findAllUser();
        return allUser;
    }


}