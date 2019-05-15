package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FiveHashrate implements Serializable {
    private String username;
    private Float difficulty;
    private Date time;
    private String our_result;
    private Long sec;
}
