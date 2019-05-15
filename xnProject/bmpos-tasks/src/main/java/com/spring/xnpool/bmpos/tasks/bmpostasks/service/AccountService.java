package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Accounts;

public interface AccountService {
    Accounts queryAccountByUserName(String userName);
}
