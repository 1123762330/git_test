package com.xn.console.bmposconsole.dao.model;

import lombok.Data;

@Data
public class RewardModel {
    private int id;

    private String coin;

    private double threshold; //单份奖励需要的余额数量阈值

    private int rewardCount; //单份奖励对应的奖励额度

    private String postUrl; //奖励份额提交地址

    private String enabled; //是否启用奖励 y,n

}
