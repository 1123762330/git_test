package com.xn.bpmworkerevents.domain;

import java.io.Serializable;

public class Result implements Serializable{
    private String userId;
    private String userName;

    public Result () {
    }

    public Result (String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    public String getUserId () {
        return userId;
    }

    public void setUserId (String userId) {
        this.userId = userId;
    }

    public String getUserName () {
        return userName;
    }

    public void setUserName (String userName) {
        this.userName = userName;
    }

    @Override
    public String toString () {
        return "Result{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
