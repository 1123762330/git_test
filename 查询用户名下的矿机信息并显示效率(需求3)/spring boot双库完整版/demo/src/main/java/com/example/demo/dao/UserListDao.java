package com.example.demo.dao;

import com.example.demo.domain.Accounts;
import com.example.demo.domain.Works;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserListDao {
    //查找用户列表
    public List<Accounts> findUserList() ;

    //查找用户名下的矿机列表
    List<Works> findWorkerList(int puid);
}
