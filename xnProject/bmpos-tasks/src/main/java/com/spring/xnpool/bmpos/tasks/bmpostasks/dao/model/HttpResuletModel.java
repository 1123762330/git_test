package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class HttpResuletModel <T> implements Serializable {
    private String message;
    private boolean success;
    private T data;
}
