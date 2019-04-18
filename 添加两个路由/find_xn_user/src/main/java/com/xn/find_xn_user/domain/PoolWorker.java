package com.xn.find_xn_user.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class PoolWorker implements Serializable {
    private Integer id;

    private Integer accountId;

    private String username;

    private String password;

    private Float difficulty;

    private Byte monitor;

    private Date lastAccessTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Float getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Float difficulty) {
        this.difficulty = difficulty;
    }

    public Byte getMonitor() {
        return monitor;
    }

    public void setMonitor(Byte monitor) {
        this.monitor = monitor;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    @Override
    public String toString() {
        return "PoolWorker{" +
                "id=" + id +
                ", accountId=" + accountId +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", difficulty=" + difficulty +
                ", monitor=" + monitor +
                ", lastAccessTime=" + lastAccessTime +
                '}';
    }
}