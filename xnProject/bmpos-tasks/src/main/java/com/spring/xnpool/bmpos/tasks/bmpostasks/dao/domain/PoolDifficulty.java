package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class PoolDifficulty implements Serializable {

    private static final long serialVersionUID = -7632309886273933120L;
    private Double difficulty;
    private Integer count;
}
