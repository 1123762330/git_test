package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllWorkerAverageForce implements Serializable{
    private Date startTime;
    private Float avgHashrate;
    private Float realHashrate;
    private String user;
}
