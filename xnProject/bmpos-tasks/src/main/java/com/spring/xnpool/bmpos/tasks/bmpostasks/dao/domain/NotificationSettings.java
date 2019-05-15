package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class NotificationSettings implements Serializable {
    private static final long serialVersionUID = -2336044710077155546L;
    private Integer id;
    private String type;
    private Integer accountId;
    private Integer active;
}
