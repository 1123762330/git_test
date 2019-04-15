package com.xn.find_xn_user.domain;


public class QueryResult<T> {
    private int err_no;
    private String err_msg;
    private Object data;

    public int getErr_no() {
        return err_no;
    }

    public void setErr_no(int err_no) {
        this.err_no = err_no;
    }

    public String getErr_msg() {
        return err_msg;
    }

    public void setErr_msg(String err_msg) {
        this.err_msg = err_msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public QueryResult(int err_no, String err_msg, Object data) {

        this.err_no = err_no;
        this.err_msg = err_msg;
        this.data = data;
    }

    public QueryResult() {

    }
}