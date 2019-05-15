package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class StateVO implements Serializable {
    private static final long serialVersionUID = 703253845733015689L;
    private Integer sid;
    private Integer success;
    private Integer failed;
    private String pluginsId;
    private List<ErrorEntry> errorEntries;
}
