package com.xn.bpmworkerevents.entity;

import lombok.Data;

@Data
public class PoolWorker {
    private Integer accountId;

    private String username;

    private String password;

    private Float difficulty;

    private Boolean monitor;

    private String lastAccessTime;
}