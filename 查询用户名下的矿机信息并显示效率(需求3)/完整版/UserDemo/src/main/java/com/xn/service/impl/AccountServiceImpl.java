package com.xn.service.impl;

import com.xn.dao.AccountDao;
import com.xn.domain.Accounts;
import com.xn.domain.Works;
import com.xn.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("accountService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    public List<Accounts> findAll() {
        System.out.println("业务层：查询所有账户...");
        return accountDao.findAll();
    }

    public void saveAccount(Accounts account) {
        System.out.println("业务层：保存帐户...");
        accountDao.saveAccount(account);
    }

    @Override
    public List<Works> findUserList(String username) {

        return accountDao.findUserList(username);
    }

    //查找所有的用户列表
    @Override
    public List<Accounts> findUsers() {
        return accountDao.findUsers();
    }

    //查找用户下的所有矿机信息
    @Override
    public List<Works> findWorkerList(int puid) {
        return accountDao.findWorkerList(puid);
    }


}
