package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class PluginsState implements Serializable {
    private static final long serialVersionUID = -5505995389649628499L;
    private Integer id;
    private Integer success = 0;
    private Integer failed = 0;
    private String pluginsId;
    private Date time;

}
