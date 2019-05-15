package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Hashrate implements Serializable {
    private Date mintime;
    private Date maxtime;
    private Date startTime;
    private Integer lastId;
    private float hashrate;
    private String username;
}
