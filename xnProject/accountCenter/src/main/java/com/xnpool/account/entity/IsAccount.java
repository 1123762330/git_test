package com.xnpool.account.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: zly
 * @Date: 2019/5/15 13:21
 */
@Data
public class IsAccount implements Serializable {
    private static final long serialVersionUID = -7479588666695888719L;
    private Integer accountId;
    private Integer isAccount;
}
