package com.xnpool.account.entity;

import lombok.Data;

@Data
public class SaleAddress extends Base{
    private static final long serialVersionUID = 1187630203217628968L;
    private Integer id;
    private Integer accountId;
    private String currency;
    private String coinAddress;
}
