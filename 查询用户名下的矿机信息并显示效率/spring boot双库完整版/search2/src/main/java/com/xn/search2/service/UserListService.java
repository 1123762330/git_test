package com.xn.search2.service;

import com.xn.search2.dao.UserListDao;
import com.xn.search2.domain.*;
import com.xn.search2.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserListService {
    @Autowired
    private UserListDao userListDao;

    //查找用户列表
    public JsonUtil<WorkVO> findUserList() {
        //程序开始时间
        long satrt = new Date().getTime();
        List<Accounts> userList = userListDao.findUserList();
        //程序结束时间
        long end = new Date().getTime();
        //程序总耗时
        long time = end - satrt;
        //将数据封装在workVO里面,返回到前台页面上
        WorkVO workVO = new WorkVO(userList, time);
        return new JsonUtil<>(200, workVO);
    }

    //统计矿池在线天数
    public JsonUtil<WorkVO> findPool() {
        //程序开始时间
        long satrt = new Date().getTime();
        List<StatsPoolDay> poolList = userListDao.findPool();
        //程序结束时间
        long end = new Date().getTime();
        //程序总耗时
        long time = end - satrt;
        //将数据封装在workVO里面,返回到前台页面上
        WorkVO workVO = new WorkVO(poolList, time);
        return new JsonUtil<>(200, workVO);
    }

    //统计矿池在线小时
    public JsonUtil<WorkVO> findPoolHour() {
        //程序开始时间
        long satrt = new Date().getTime();
        List<StatsPoolHour> poolHourList = userListDao.findPoolHour();
        //程序结束时间
        long end = new Date().getTime();
        //程序总耗时
        long time = end - satrt;
        //将数据封装在workVO里面,返回到前台页面上
        WorkVO workVO = new WorkVO(poolHourList, time);
        return new JsonUtil<>(200, workVO);
    }

    //统计用户在线天数
    public JsonUtil<WorkVO> findUserDay(int puid) {
        //程序开始时间
        long satrt = new Date().getTime();
        List<StatsUsersDay> usersDayList = userListDao.findUserDay(puid);
        //程序结束时间
        long end = new Date().getTime();
        //程序总耗时
        long time = end - satrt;
        //将数据封装在workVO里面,返回到前台页面上
        WorkVO workVO = new WorkVO(usersDayList, time);
        return new JsonUtil<>(200, workVO);
    }

    //统计用户在线小时
    public JsonUtil<WorkVO> findUsersHour(int puid) {
        //程序开始时间
        long satrt = new Date().getTime();
        List<StatsUsersHour> usersHourList = userListDao.findUsersHour(puid);
        //程序结束时间
        long end = new Date().getTime();
        //程序总耗时
        long time = end - satrt;
        //将数据封装在workVO里面,返回到前台页面上
        WorkVO workVO = new WorkVO(usersHourList, time);
        return new JsonUtil<>(200, workVO);
    }

    //查询矿机在线天数
    public JsonUtil<WorkVO> findWorkersDay(int puid) {
        //程序开始时间
        long start = new Date().getTime();
        List<StatsWorkersDay> workersDayList = userListDao.findWorkersDay(puid);
        //程序结束时间
        long end = new Date().getTime();
        //程序总耗时
        long time = end - start;
        //将数据封装在workVO里面,返回到前台页面上
        WorkVO workVO = new WorkVO(workersDayList, time);
        return new JsonUtil<>(200, workVO);
    }

    //查询矿机在线小时
    public JsonUtil<WorkVO> findWorkersHour(int puid) {
        //程序开始时间
        long satrt = new Date().getTime();
        List<StatsWorkersHour> workersHourList = userListDao.findWorkersHour(puid);
        //程序结束时间
        long end = new Date().getTime();
        //程序总耗时
        long time = end - satrt;
        //将数据封装在workVO里面,返回到前台页面上
        WorkVO workVO = new WorkVO(workersHourList, time);
        return new JsonUtil<>(200, workVO);
    }
}
