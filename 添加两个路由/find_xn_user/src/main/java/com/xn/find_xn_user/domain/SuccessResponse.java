package com.xn.find_xn_user.domain;

import java.io.Serializable;

public class SuccessResponse implements Serializable {
    private String username;
    private Integer id;
    private Integer status;

    public SuccessResponse() {
    }

    public SuccessResponse(String username, Integer id, Integer status) {
        this.username = username;
        this.id = id;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
