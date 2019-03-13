package com.xn.demo.dao;

import com.xn.demo.domain.PoolWorker;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper
public interface MessageDao {
    //查询方法
    public List<PoolWorker> findMessageList() ;

    //save方法
    //public void saveMessage(PoolWorker poolWorker);
    public void saveMessage(@Param("user_id") Integer userId, @Param("user_name") String username);

}
