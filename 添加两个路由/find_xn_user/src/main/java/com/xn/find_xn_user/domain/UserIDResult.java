package com.xn.find_xn_user.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

public class UserIDResult implements Serializable {

    private Integer id;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
    private String message;

    private Integer status;


    public UserIDResult(Integer id, Integer status) {
        this.id = id;
        this.status = status;
    }

    public UserIDResult(String message, Integer status) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "UserIDResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", id=" + id +
                '}';
    }
}
