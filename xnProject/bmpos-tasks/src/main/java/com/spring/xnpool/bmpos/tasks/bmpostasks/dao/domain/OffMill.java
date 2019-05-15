package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class OffMill implements Serializable {
    private static final long serialVersionUID = 2136003009262431739L;
    private String user;
    private String millName;
    private String address;
    private String coin;
    private String time;
}
