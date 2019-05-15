package com.xn.bmpos.manager.bmposmanager.domain.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


@Data
public class Setting {
    private int id;

    private String name;

    private String fee;

    private double blockPrice;

    private String rpchost;

    private String node;

    private String value;

    private String marke;

    private String isAccept;

    private Integer unit;


}
