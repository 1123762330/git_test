package com.xn.bmpos.api.bmposapi.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: zly
 * @Date: 2019/5/13 10:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsersAndCoins {
    private Integer accountId;
    private String name;
    private String coinAddress;
    private String currency;
}
