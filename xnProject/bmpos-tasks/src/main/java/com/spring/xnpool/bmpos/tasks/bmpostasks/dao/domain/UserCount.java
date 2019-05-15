package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserCount implements Serializable {
    private static final long serialVersionUID = 3283742176207602970L;
    private String user;
    private Integer count;
}
