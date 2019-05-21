package com.xnpool.account.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Authorinsing implements Serializable {
    private static final long serialVersionUID = 8853020509762219445L;
    private String name;//被授权用户名
    private String authority; //授予权利
}
