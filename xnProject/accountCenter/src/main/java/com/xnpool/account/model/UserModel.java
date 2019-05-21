package com.xnpool.account.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserModel {
    private int userid;
    private String username;
    private String phone;
    private String email;
    private String password;
    private int state;
    private String street;
    private String IP;
    private String rolename;
    private String roleremark;

}
