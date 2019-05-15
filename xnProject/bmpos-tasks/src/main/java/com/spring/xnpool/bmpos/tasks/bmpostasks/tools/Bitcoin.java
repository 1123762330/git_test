package com.spring.xnpool.bmpos.tasks.bmpostasks.tools;


import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class Bitcoin {

    /**
     * 根据难度和一个币的自定义价格，计算出pps支付价格
     *
     * @param pps_reward
     * @param dDifficulty
     * @return
     */
    public double calcPPSValue(String coin, double pps_reward, BigDecimal dDifficulty) {
        int target_bits = 0;
        /*
         * https://github.com/MPOS/php-mpos/tree/236cccd5d3178e99504bf2665db8bddfed6aa176/include/classes/coins
         *
         * coin_neoscrypt   16
         *
         * coin_scrypt.class.php 16
         *
         * oin_scryptn.class.php 16
         *
         * coin_sha256d.class.php 32
         *
         * coin_x13.class.php 24
         *
         * coin_x15.class.php 24
         *
         * */
        switch (coin) {
            case "ltc":
                target_bits = 0;
                break;
            case "eth":
                target_bits = 16;
                break;
            case "etz":
                target_bits = 24;
                break;
            case "btc":
                target_bits = 32;
                break;
        }
        return (pps_reward / (Math.pow(2, target_bits) * dDifficulty.doubleValue()));
    }

    /**
     * 从币种配置文件获取精度
     *
     * @return
     */
    public int getCoinValuePrevision(String coin) {

        /*
         * coin_sha256d.class.php = 20
         * */
        int coin_value_precision = 0;

        switch (coin) {
            case "ltc":
                coin_value_precision = 8;
                break;
            case "eth":
                coin_value_precision = 20;
                break;
            case "etz":
                coin_value_precision = 8;
                break;
            case "btc":
                coin_value_precision = 8;
                break;
        }
        return coin_value_precision;
    }
}
