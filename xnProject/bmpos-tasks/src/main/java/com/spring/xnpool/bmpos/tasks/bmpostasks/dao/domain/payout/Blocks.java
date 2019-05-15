package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.payout;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Blocks implements Serializable {
    private static final long serialVersionUID = 338886548761023009L;
    private Integer id; //int(10) unsigned块ID
    private Integer height; //int(10) unsigned高
    private String blockhash; //char(65)哈希块
    private Integer confirmations; //int(10)证实
    private BigDecimal amount; //double数量
    private  BigDecimal difficulty; //double难度值
    private Integer time;//int(11)
    private Integer accounted;// tinyint(1)是否考虑
    private Integer accountId; //int(255) unsigned账户ID
    private String workerName; //varchar(50)工作者
    private Long shares; //bigint(30) //unsigned分享计数
    private Long shareId;//bigint(30)上游有效共享ID



}
