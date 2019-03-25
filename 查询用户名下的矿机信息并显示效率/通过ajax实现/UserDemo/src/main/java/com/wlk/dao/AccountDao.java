package com.wlk.dao;


import com.wlk.domain.Accounts;
import com.wlk.domain.Works;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 帐户dao接口
 */
@Repository
public interface AccountDao {

    // 查询所有账户
    @Select("select * from accounts")
    public List<Accounts> findAll();

    // 保存帐户信息
    @Insert("insert into accounts (username,pass,pin) values (#{username},#{pass},#{pin})")
    public void saveAccount(Accounts account);

    //根据用户名查找所有的用户下面的矿机
    @Select("SELECT * FROM mining_workers WHERE puid=(SELECT id FROM accounts WHERE username=#{username})")
    public List<Works> findUserList(String username);

    //根据用户名是否存在
    @Select("SELECT * FROM accounts WHERE username=#{username}")
    public Accounts findUser(String username);
}
