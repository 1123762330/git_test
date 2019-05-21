package com.xnpool.account.service.impl;

import com.xnpool.account.entity.EmailSetting;
import com.xnpool.account.mappers.EmailSettingMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * FileName:       EmailSettingService
 * Author:         Administrator
 * Date:           2019/5/17 10:40
 * Description:
 */
@Service
public class EmailSettingService {

    @Autowired
    private EmailSettingMapper emailSettingMapper;

    public EmailSetting findEmailSetting(){
        EmailSetting emailSetting = emailSettingMapper.findEmailSetting();
        if(emailSetting == null){
            System.out.println("获取邮箱设置出错");
        }
        return emailSetting;
    }
}
