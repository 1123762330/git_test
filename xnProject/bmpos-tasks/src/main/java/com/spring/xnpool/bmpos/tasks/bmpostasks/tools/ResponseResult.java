package com.spring.xnpool.bmpos.tasks.bmpostasks.tools;

/**
 * 返回json数据
 */

import java.io.Serializable;

public class ResponseResult<T> implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -1626793180717240861L;
    private Integer state;
    private String message;
    private T data;

    public ResponseResult() {
        super();
    }

    public ResponseResult(Integer state) {
        super();
        setState(state);
    }

    public ResponseResult(Integer state, String message) {
        this(state);
        setMessage(message);
    }

    public ResponseResult(Integer state, Exception e) {
        this(state, e.getMessage());
    }

    public ResponseResult(Integer state, T data) {
        this(state);
        setData(data);
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseResult [state=" + state + ", message=" + message + ", data=" + data + "]";
    }

}
