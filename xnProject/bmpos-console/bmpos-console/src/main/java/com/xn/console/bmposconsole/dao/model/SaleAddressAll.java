package com.xn.console.bmposconsole.dao.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @zly
 */
@Data
public class SaleAddressAll implements Serializable {
    private Integer id;

    private Integer accountId=0;

    private String currency="";

    private String coinAddress="";

    private String createdAt = "";

    private String updatedAt = "";

    private String deletedAt = "";

    /**
     * 级别表Id
     */
    private Integer settleShare=0;

    /**
     * 是否是匿名挖矿
     */
    private Byte isaccount=0;

    private String rank="";

    private Double scale=0.0;

    private String accountName="";

    private String users="";

    private static final long serialVersionUID = 1L;
}