package com.xnpool.account.model;

import lombok.Data;

@Data
public class HttpResultJsonModel<T> {
    private int state;

    private String resultCode;

    private String alertMessage;

    private T content;
}
