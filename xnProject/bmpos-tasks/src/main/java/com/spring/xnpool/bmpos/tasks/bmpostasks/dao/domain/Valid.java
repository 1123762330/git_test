package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Valid {
    private String user;
    private String ourResult;
    private Integer count;
    private Date time;
}
