package com.xn.bpmworkerevents.domain;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author zly123
 * @since 2019-05-20
 */
@TableName("pool_worker")
public class PoolWorker implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    @TableField("account_id")
    private Integer accountId;
    private String username;
    private String password;
    private Float difficulty;
    private Integer monitor;
    @TableField("last_access_time")
    private Date lastAccessTime;


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

    public Integer getMonitor() {
        return monitor;
    }

    public void setMonitor(Integer monitor) {
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
        ", username=" + username +
        ", password=" + password +
        ", difficulty=" + difficulty +
        ", monitor=" + monitor +
        ", lastAccessTime=" + lastAccessTime +
        "}";
    }
}
