package com.xn.find_xn_user.dao;

import com.xn.find_xn_user.domain.Accounts;
import com.xn.find_xn_user.domain.RegisterResult;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserListDao {
    public List<Accounts> findUserList(String last_id) ;

    Integer findUsernameById(String username);

    List findAllUser();

    void saveUser(RegisterResult registerResult);
}
