package com.xnpool.account.model;

import lombok.Data;

import java.io.Serializable;

/**
 * FileName:       IpVo
 * Author:         Administrator
 * Date:           2019/5/13 15:39
 * Description:
 */
@Data
public class IpVo implements Serializable {
    private Integer code;
    private Address address;

    @Data
    public class Address implements Serializable {
        private String ip;
        private String region;
        private String city;
    }

}
