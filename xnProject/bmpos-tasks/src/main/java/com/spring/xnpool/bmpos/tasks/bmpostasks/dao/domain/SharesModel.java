package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Data
public class SharesModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Long id;

    @Column(name = "rem_host")
    private String remHost;

    /**
     * 账户
     */
    private String userName;

    /**
     * 账户  机器名
     */
    private String nameValue;

    @Column(name = "our_result")
    private String ourResult;

    @Column(name = "upstream_result")
    private String upstreamResult;

    private String reason;

    private String solution;

    private Float difficulty;

    private Date time;
}
