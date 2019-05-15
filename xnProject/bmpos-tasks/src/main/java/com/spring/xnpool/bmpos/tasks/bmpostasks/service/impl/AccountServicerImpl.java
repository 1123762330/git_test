package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Accounts;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.AccountsMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServicerImpl implements AccountService {
    @Autowired
    private AccountsMapper accountsMapper;

    @Override
    public Accounts queryAccountByUserName(String userName) {
       Accounts account=   accountsMapper.queryAccountByUserName(userName);
        return account;
    }

}
