package com.xn.shares_pointer_log2.domain;

import java.io.Serializable;

/**
 * @author 
 */
public class Shares_old implements Serializable {
    private Long id;

    private String remHost;

    /**
     * 账户+机器名
     */
    private String username;

    private String ourResult;

    private String upstreamResult;

    private String reason;

    private String solution;

    private Integer difficulty;

    private String time;

    private String height;

    private String nonce;

    private Integer workerId;

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

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Integer getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public Shares_old() {
    }

    public Shares_old(Long id, String remHost, String username, String ourResult, String upstreamResult, String reason, String solution, Integer difficulty, String time, String height, String nonce, Integer workerId) {
        this.id = id;
        this.remHost = remHost;
        this.username = username;
        this.ourResult = ourResult;
        this.upstreamResult = upstreamResult;
        this.reason = reason;
        this.solution = solution;
        this.difficulty = difficulty;
        this.time = time;
        this.height = height;
        this.nonce = nonce;
        this.workerId = workerId;
    }
}