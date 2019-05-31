package com.cn.kafkaconsumer.dao.model;

import lombok.Data;

/**
 * @Author: zly
 * @Date: 2019/5/22 20:46
 */
@Data
public class SharesModel {
    private String rem_host;
    private String username;
    private String our_result;
    private String upstream_result;
    private String reason;
    private String solution;
    private String difficulty;

}
