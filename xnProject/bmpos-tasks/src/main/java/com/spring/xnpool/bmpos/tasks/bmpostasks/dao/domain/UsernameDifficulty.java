package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UsernameDifficulty implements Serializable {

    private static final long serialVersionUID = 3524863590094719591L;
    private String username;
    private Double difficulty;
    private Date time;
}
