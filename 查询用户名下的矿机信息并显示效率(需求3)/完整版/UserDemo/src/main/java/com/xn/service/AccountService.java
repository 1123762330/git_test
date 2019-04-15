package com.xn.service;

import com.xn.domain.Accounts;
import com.xn.domain.Works;

import java.util.List;

public interface AccountService {

    // 查询所有账户
    public List<Accounts> findAll();

    // 保存帐户信息
    public void saveAccount(Accounts account);

    //根据用户名查找所有的用户下面的矿机
    public List<Works> findUserList(String username);

    //查找用户列表
    public List<Accounts> findUsers();

    public List<Works> findWorkerList(int puid);
}
