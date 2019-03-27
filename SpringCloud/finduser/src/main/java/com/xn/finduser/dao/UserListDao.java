package com.xn.finduser.dao;

import com.xn.finduser.domain.Accounts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
public interface UserListDao {

    @Select("select  * from  accounts")
    public List<Accounts> findUserList() ;

}
