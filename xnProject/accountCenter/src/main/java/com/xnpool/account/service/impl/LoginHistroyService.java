package com.xnpool.account.service.impl;

import com.xnpool.account.entity.LoginHistroy;
import com.xnpool.account.mappers.LoginHistroyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * FileName:       LoginHistroyHistroyService
 * Author:         Administrator
 * Date:           2019/5/13 13:59
 * Description:
 */
@Service
public class LoginHistroyService {

    @Autowired
    private LoginHistroyMapper loginHistroyMapper;

    //插入一条登陆历史记录
    public int insertLoginHistroy(int userId,String loginIp,String loginPlace,String loginFacility){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd HH:mm");
        int i = 0;
        i = loginHistroyMapper.insertLoginHistroy(userId,loginIp,loginPlace,loginFacility,sdf.format(date));
        if(i>0){
            System.out.println(userId + "登陆历史插入成功");
        }else{
            System.out.println(userId + "登陆历史插入失败");
        }
        return i;
    }

    //根据userId查询 登陆历史6条
    public List<LoginHistroy> getLoginHsByUid(int userId){
        List<LoginHistroy> list = loginHistroyMapper.getLoginHsByUid(userId);
        if(list.isEmpty()){
            System.out.println("查询登陆历史为空");
        }
        return list;
    }

}
