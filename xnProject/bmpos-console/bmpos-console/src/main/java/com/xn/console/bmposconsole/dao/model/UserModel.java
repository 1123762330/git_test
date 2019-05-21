package com.xn.console.bmposconsole.dao.model;

import lombok.Data;

@Data
public class UserModel {
    private int userid;
    private String username;
    private String password;
    private String phone;
    private String email;
    private int state;
    private String street;
    private String IP;
    private String createtime;
    private String rolename;
    private String roleremark;

}
