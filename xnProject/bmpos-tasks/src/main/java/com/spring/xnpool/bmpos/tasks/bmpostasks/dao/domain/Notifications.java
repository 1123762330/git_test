package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Notifications implements Serializable {
    private static final long serialVersionUID = 6496676004886129229L;
    private Integer id;//int(11) unsigned
    private String type; //varchar(25)
    private String data;//varchar(255)
    private Integer active; //tinyint(1)
    private String time; //timestamp
    private Integer accountId;//int(10) unsigned
}
