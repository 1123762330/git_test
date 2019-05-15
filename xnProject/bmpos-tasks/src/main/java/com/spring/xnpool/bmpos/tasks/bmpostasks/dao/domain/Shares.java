package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import javax.persistence.*;
import java.util.Date;

public class Shares {
    @Id
    private Long id;

    @Column(name = "rem_host")
    private String remHost;

    /**
     * 账户+机器名
     */
    private String username;

    @Column(name = "our_result")
    private String ourResult;

    @Column(name = "upstream_result")
    private String upstreamResult;

    private String reason;

    private String solution;

    private Float difficulty;

    private Date time;

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return rem_host
     */
    public String getRemHost() {
        return remHost;
    }

    /**
     * @param remHost
     */
    public void setRemHost(String remHost) {
        this.remHost = remHost;
    }

    /**
     * 获取账户+机器名
     *
     * @return username - 账户+机器名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置账户+机器名
     *
     * @param username 账户+机器名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return our_result
     */
    public String getOurResult() {
        return ourResult;
    }

    /**
     * @param ourResult
     */
    public void setOurResult(String ourResult) {
        this.ourResult = ourResult;
    }

    /**
     * @return upstream_result
     */
    public String getUpstreamResult() {
        return upstreamResult;
    }

    /**
     * @param upstreamResult
     */
    public void setUpstreamResult(String upstreamResult) {
        this.upstreamResult = upstreamResult;
    }

    /**
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return solution
     */
    public String getSolution() {
        return solution;
    }

    /**
     * @param solution
     */
    public void setSolution(String solution) {
        this.solution = solution;
    }

    /**
     * @return difficulty
     */
    public Float getDifficulty() {
        return difficulty;
    }

    /**
     * @param difficulty
     */
    public void setDifficulty(Float difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Date time) {
        this.time = time;
    }
}