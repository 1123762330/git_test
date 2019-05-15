package com.xn.bmpos.api.bmposapi.domain.dao;

import lombok.Data;

/**
 * FileName:       Machine
 * Author:         Administrator
 * Date:           2019/4/29 14:59
 * Description:
 */
@Data
public class Machine {

    private int id;

    private String mine_type;//矿机名称

    private String hashtate;

    private int power;

    private int coin_id;

    private double Electricity;//电费

}
