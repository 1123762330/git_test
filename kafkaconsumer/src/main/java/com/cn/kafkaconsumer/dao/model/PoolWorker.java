package com.cn.kafkaconsumer.dao.model;

import lombok.Data;

import java.util.Date;

@Data
public class PoolWorker {
    /**
     *
     */
    private Integer id;

    /**
     *
     */
    private Integer accountId;

    /**
     *
     */
    private String username;

    /**
     *
     */
    private String password;

    /**
     *
     */
    private Float difficulty;

    /**
     *
     */
    private Boolean monitor;

    /**
     *
     */
    private String lastAccessTime;
}