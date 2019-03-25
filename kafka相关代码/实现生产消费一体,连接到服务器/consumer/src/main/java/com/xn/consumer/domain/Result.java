package com.xn.consumer.domain;

import java.io.Serializable;

public class Result implements Serializable{
    private String created_at;
    private String type;
    private Object content;

    public Result() {
    }

    public Result(String created_at, String type, Object content) {
        this.created_at = created_at;
        this.type = type;
        this.content = content;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Result{" +
                "created_at='" + created_at + '\'' +
                ", type='" + type + '\'' +
                ", content=" + content +
                '}';
    }
}
