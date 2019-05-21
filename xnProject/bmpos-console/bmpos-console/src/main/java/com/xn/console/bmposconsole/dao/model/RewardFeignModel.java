package com.xn.console.bmposconsole.dao.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class RewardFeignModel<T> implements Serializable {
    private String message;
    private boolean success;
    private T data;
}
