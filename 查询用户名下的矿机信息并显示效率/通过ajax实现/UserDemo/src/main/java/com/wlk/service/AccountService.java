package com.wlk.service;

import com.wlk.domain.Accounts;
import com.wlk.domain.Works;

import java.util.List;

public interface AccountService {

    // 查询所有账户
    public List<Accounts> findAll();

    // 保存帐户信息
    public void saveAccount(Accounts account);

    //根据用户名查找所有的用户下面的矿机
    public List<Works> findUserList(String username);

    //判断用户名是否存在
    public Accounts findUser(String username);

}
