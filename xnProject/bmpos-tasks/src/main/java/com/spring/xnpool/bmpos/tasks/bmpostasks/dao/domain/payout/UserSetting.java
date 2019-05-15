package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.payout;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class UserSetting implements Serializable {
    private static final long serialVersionUID = -3344470641202802944L;
    private Integer accountId;
    private String name;
    private String value;
}
