package com.xnpool.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName:       Email
 * Author:         Administrator
 * Date:           2019/5/17 10:26
 * Description:    邮箱配置
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailSetting {
    private int id;
    private String username;//用户名
    private String password;//密码或授权码
    private String smtp;//服务器
    private String port;//端口
    private String defaultEncoding;//默认编码
    private String protocol;//使用的协议（JavaMail规范要求）
    private String auth;//验证
    private String socketFactory;//class
}
