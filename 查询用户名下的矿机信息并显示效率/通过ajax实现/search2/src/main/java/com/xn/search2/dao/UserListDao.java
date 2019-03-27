package com.xn.search2.dao;

import com.xn.search2.domain.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserListDao {
    //查找用户列表
    List<Accounts> findUserList() ;

    //查找用户名下的矿机列表
    List<Works> findWorkerList(int puid);

    //统计矿池在线天数
    List<StatsPoolDay> findPool();

    //统计矿池在线小时
    List<StatsPoolHour> findPoolHour();

    //统计用户在线天数
    List<StatsUsersDay> findUserDay();

    //统计用户在线小时
    List<StatsUsersHour> findUsersHour();

    //统计矿机在线天数
    List<StatsWorkersDay> findWorkersDay();

    //统计矿机在线小时
    List<StatsWorkersHour> findWorkersHour();
}
