package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Table(name = "bit_coin")
public class BitCoin {
    private String type;

    private String username;

    private String password;

    private Integer host;

    @Column(name = "debug_level")
    private String debugLevel;

    @Column(name = "debug_object")
    private String debugObject;

    private String memcache;
}