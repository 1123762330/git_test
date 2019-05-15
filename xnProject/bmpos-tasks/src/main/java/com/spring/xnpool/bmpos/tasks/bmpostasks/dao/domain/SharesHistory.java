package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SharesHistory implements Serializable {
    private static final long serialVersionUID = 7889551649778092696L;
    private Long id;
    private String username;
    private Float difficulty;
    private Integer counts;
    private Date times;
    private String ourResult;
}
