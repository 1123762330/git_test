package com.xn.bmpos.manager.bmposmanager.domain.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName:       CoinType
 * Author:         Administrator
 * Date:           2019/5/16 12:04
 * Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoinType {
    private int id;
    private String coinName;
    private String price;
}
