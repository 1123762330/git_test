package com.xnpool.account.model;

import lombok.Data;

@Data
public class HttpResultUpdateModel<T> {
    private String message;
    private boolean success;
    private T data;
}
