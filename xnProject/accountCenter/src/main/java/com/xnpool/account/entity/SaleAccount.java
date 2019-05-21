package com.xnpool.account.entity;

import lombok.Data;

@Data
public class SaleAccount extends Base {
    private static final long serialVersionUID = 1798215070716988803L;
    private Integer id;
    private Integer userId;
    private String name;
    private String password = "123456";
    private String remarkName;
}
