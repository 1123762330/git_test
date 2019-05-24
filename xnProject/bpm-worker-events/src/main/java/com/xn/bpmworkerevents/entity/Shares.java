package com.xn.bpmworkerevents.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * FileName:       ShareModel
 * Author:         Administrator
 * Date:           2019/5/22 17:49
 * Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Shares {

    private String rem_host;
    private String username;
    private String our_result;
    private String upstream_result;
    private String reason;
    private String solution;
    private String difficulty;

}
