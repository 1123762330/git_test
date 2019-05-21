package com.xn.bmpos.manager.bmposmanager.domain.model;

import lombok.Data;

import java.util.Date;

@Data
public class RewardModel {

    private int id;

    private String coin;

    private String accountName;

    private String accountAddress;

    private String mark;

    private Date time;

    private double ispaid; //已经支付的 ==本次支付

    private double isNotPaid;// 待支付的 == 余额

}
