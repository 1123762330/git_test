package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
@Data
@AllArgsConstructor
public class ErrorEntry {
    private Integer id;
    private String message;
    private Date time;
    private String pluginsId;
    public ErrorEntry() {

    }
}
