package com.xn.bmpos.api.bmposapi.domain.model;

import lombok.Data;

@Data
public class CoinIndexModel {
    private String coin;

    //日实时收益
    private double Earnings;

    //全网算力
    private double NetHashrate;

    //矿池算力
    private double Hashrate;

    //全网难度
    private String NetDifficulty;

    //在线矿工
    private int MinersTotal;

    //矿机
    private int MinerCount;

}
