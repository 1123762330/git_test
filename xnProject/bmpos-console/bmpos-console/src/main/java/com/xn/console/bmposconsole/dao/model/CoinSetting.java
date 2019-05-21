package com.xn.console.bmposconsole.dao.model;

import lombok.Data;

@Data
public class CoinSetting {
    private int id;

    private String name;

    private String fee;

    private double blockPrice;

    private String rpchost;

    private String node;

    private String value;

    private String image;

    private String unit;

    private Integer scale;

    private String isAccept;
}
