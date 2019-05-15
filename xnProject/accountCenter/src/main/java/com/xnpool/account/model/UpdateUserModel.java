package com.xnpool.account.model;

import lombok.Data;

/**
 * 修改账户操作
 */
@Data
public class UpdateUserModel {
    private String phone;
    private String email;
    private String username;
    private String pwd;
    private String street;//收货地址
}
