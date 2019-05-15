package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class UserDifficulty implements Serializable {

    private static final long serialVersionUID = 3524863590094719591L;
    private String user;
    private Double difficulty;
    private Date time;
}
