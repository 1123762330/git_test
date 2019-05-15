package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class CountWorker implements Serializable {
    private static final long serialVersionUID = 3447918261287980541L;
    private Integer count;
    private String user;
}
