package com.xn.bmpos.manager.bmposmanager.domain.model;

import lombok.Data;

/**
 * 修改账户操作
 */
@Data
public class UpdateUserModel {

    private int type;  //表示0 表示 手机号， 1 表示邮箱验证

    private String phone;

    private String email;

    private String username;

    private String pwd;

    private String street;

    private String code;
}
