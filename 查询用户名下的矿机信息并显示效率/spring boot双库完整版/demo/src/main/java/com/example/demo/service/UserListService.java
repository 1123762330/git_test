package com.example.demo.service;

import com.example.demo.dao.UserListDao;
import com.example.demo.domain.Accounts;
import com.example.demo.domain.WorkVO;
import com.example.demo.domain.Works;
import com.example.demo.utils.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class UserListService {
    @Autowired
    private UserListDao userListDao;

    //查找用户列表
    public List<Accounts> findUserList() {
        List<Accounts> accounts = userListDao.findUserList();
        return accounts;
    }

    //点击用户名显示用户名下矿机信息
    public JsonUtil<WorkVO> findWorkerList(int puid) {
        long satrt = new Date().getTime();
        List<Works> workerList = userListDao.findWorkerList(puid);
        //程序结束时间
        long end = new Date().getTime();
        //程序总耗时
        long time = end - satrt;
        //将数据封装在workVO里面,返回到前台页面上
        WorkVO workVO = new WorkVO(workerList, time);
        return new JsonUtil<>(200, workVO);
    }
}
