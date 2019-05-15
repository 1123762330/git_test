package com.xnpool.account.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Role {
    private int roleid; // 初始值 0-管理员 1-普通用户

    private String rolename; //权限名称

    private String roleremark; // 權限描述

    private Date createtime;

    private Date updatetime;
}
