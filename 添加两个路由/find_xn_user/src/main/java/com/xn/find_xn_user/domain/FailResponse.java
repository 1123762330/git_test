package com.xn.find_xn_user.domain;

import java.io.Serializable;

public class FailResponse implements Serializable{
    private String message;
    private Integer status;

    public FailResponse() {
    }

    public FailResponse(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
