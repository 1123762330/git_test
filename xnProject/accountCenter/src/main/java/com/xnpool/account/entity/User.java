package com.xnpool.account.entity;


import lombok.Data;

import java.util.Date;

@Data
public class User {

    private int userid;

    private int roleid;


    private String name;

    private String email;


    private String mobile;


    private String pwd;


    private String code;


    private Date codesenttime;


    private String enableflag;


    private String nick;


    private String qq;


    private String head;


    private String sex;

    private String company;

    private String job;

    private String intro;

    private String qrcode;

    private String street;//收货地址

    private Date createtime;

    private Date logintime;

    private String wxopenid;

    private String wxhead;

    private String wxnick;

    private String dealerflag;

    private Integer trynum;

    private String IP; //最近登录的iP

    private int state;  //使用状态  1  使用  0 没使用

    private int is_app; // 判断是否是用户是否使用app 0表示不是，1 标识是


    private boolean isAdmin;

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public boolean getIsAdmin() {
        return this.isAdmin;
    }
}
