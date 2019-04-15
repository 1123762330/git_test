package com.xn.shares_pointer_log2.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Shares implements Serializable {
    private Long id;

    private String remHost;

    private String username;

    private String ourResult;

    private String upstreamResult;

    private String reason;

    private String solution;

    private Float difficulty;

    private Date time;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRemHost() {
        return remHost;
    }

    public void setRemHost(String remHost) {
        this.remHost = remHost;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOurResult() {
        return ourResult;
    }

    public void setOurResult(String ourResult) {
        this.ourResult = ourResult;
    }

    public String getUpstreamResult() {
        return upstreamResult;
    }

    public void setUpstreamResult(String upstreamResult) {
        this.upstreamResult = upstreamResult;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public Float getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Float difficulty) {
        this.difficulty = difficulty;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}