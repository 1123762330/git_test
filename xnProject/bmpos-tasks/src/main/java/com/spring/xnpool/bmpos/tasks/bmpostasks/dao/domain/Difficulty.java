package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Difficulty implements Serializable {
    private static final long serialVersionUID = 8132899876296069941L;
    Float difficulty;
    Date time;
}
