package com.xnpool.account.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SaleAccountVO extends Base{
    private static final long serialVersionUID = -8128669474394011245L;
    private Integer userId;
    private Integer accountId;
    private String currency;
    private String coinAddress;
    private Double scale;
    private String rank;
}
