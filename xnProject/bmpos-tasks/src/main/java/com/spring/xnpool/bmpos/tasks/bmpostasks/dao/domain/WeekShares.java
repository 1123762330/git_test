package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeekShares {
    private String username;
    private Float difficulty;
    private Float avghashrate;
    private Date time;
}
