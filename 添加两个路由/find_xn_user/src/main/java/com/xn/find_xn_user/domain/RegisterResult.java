package com.xn.find_xn_user.domain;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY )
public class RegisterResult {
    private String message;
    private String username;
    private String password;
    private Integer id;
    private Integer status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getpassword() {
        return password;
    }

    public void setpassword(String password) {
        this.password = password;
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

    public RegisterResult(String message, String username, String password, Integer id, Integer status) {
        this.message = message;
        this.username = username;
        this.password = password;
        this.id = id;
        this.status = status;
    }

    public RegisterResult() {
    }

    public RegisterResult(String username, Integer id, Integer status) {
        this.username = username;
        this.id = id;
        this.status = status;
    }

    public RegisterResult(String message, Integer status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return "UserRegisterResult{" +
                "message='" + message + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", id=" + id +
                ", status=" + status +
                '}';
    }
}
