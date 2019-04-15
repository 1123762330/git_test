package com.xn.dao;

import com.xn.domain.Accounts;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserListDao {
    public List<Accounts> findUserList(String last_id) ;

}
