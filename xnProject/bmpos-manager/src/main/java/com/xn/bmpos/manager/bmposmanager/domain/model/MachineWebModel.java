package com.xn.bmpos.manager.bmposmanager.domain.model;

import lombok.Data;

/**
 * FileName:       MachineWebModel
 * Author:         Administrator
 * Date:           2019/4/29 15:03
 * Description:
 */
@Data
public class MachineWebModel {

    private String mineType;  //矿机型号

    private String coinTypeName; //币种名称

    private String hashrate; //算力

    private int power; //功耗

    private double nissan;  //日产出

    private double electricityBill;//电费支出

    private long electricityProportion;//电费占比

    private double netProfit;//净利润

    private long hs;

}
