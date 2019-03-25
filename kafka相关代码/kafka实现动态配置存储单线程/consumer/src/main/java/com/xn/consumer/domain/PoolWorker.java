package com.xn.consumer.domain;

import java.io.Serializable;

public class PoolWorker implements Serializable {
    private Integer user_id;
    private String user_name;
    private Long worker_id;
    private String worker_name;
    private String client_agent;
    private String ip;
    private String session_id;

    public PoolWorker() {
    }

    public PoolWorker(Integer user_id, String user_name, Long worker_id, String worker_name, String client_agent, String ip, String session_id) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.worker_id = worker_id;
        this.worker_name = worker_name;
        this.client_agent = client_agent;
        this.ip = ip;
        this.session_id = session_id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Long getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(Long worker_id) {
        this.worker_id = worker_id;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public String getClient_agent() {
        return client_agent;
    }

    public void setClient_agent(String client_agent) {
        this.client_agent = client_agent;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }

    @Override
    public String toString() {
        return "PoolWorker{" +
                "user_id=" + user_id +
                ", user_name='" + user_name + '\'' +
                ", worker_id=" + worker_id +
                ", worker_name='" + worker_name + '\'' +
                ", client_agent='" + client_agent + '\'' +
                ", ip='" + ip + '\'' +
                ", session_id='" + session_id + '\'' +
                '}';
    }
}