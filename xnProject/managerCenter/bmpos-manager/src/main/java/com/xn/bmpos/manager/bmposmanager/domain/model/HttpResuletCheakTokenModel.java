package com.xn.bmpos.manager.bmposmanager.domain.model;

import lombok.Data;

import java.util.List;

@Data
public class HttpResuletCheakTokenModel {
    private String user_nickname; //用户昵称
    private String user_name;   //相当于用户登录用的标识 ---
    private String userPhone;
    private String userEmail;
    private int is_app;
    private List<String> scope;
    private boolean active;
    private String exp;
    private List<String> authorities;
    private String jti;
    private String client_id;
}
