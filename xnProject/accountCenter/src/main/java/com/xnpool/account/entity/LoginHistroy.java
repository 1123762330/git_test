package com.xnpool.account.entity;

import lombok.Data;

/**
 * FileName:       LoginHistroy
 * Author:         Administrator
 * Date:           2019/5/13 13:42
 * Description:
 */
@Data
public class LoginHistroy {

    private int id;

    private int userId;//用户id

    private String loginIp;//登陆ip

    private String loginPlace;//登陆地址

    private String loginFacility;//登陆设备

    private String loginTime;//登陆时间

}
