package com.xn.shares_pointer_log2.domain;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Log2Shares implements Serializable {
    private Long id;

    private String ourResult;

    private String upstreamResult;

    private String reason;

    private String solution;

    private Float difficulty;

    private Integer bits;

    private String nonce;

    private static final long serialVersionUID = 1L;

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getBits() {
        return bits;
    }

    public void setBits(Integer bits) {
        this.bits = bits;
    }

    public Log2Shares() {
    }

    public Log2Shares(String ourResult, String upstreamResult, String reason, String solution, Float difficulty, Integer bits, String nonce) {
        this.ourResult = ourResult;
        this.upstreamResult = upstreamResult;
        this.reason = reason;
        this.solution = solution;
        this.difficulty = difficulty;
        this.bits = bits;
        this.nonce = nonce;
    }

    public Log2Shares(String ourResult, String reason, Integer bits, String nonce) {
        this.ourResult = ourResult;
        this.reason = reason;
        this.bits = bits;
        this.nonce = nonce;
    }

    @Override
    public String toString() {
        return "Shares2{" +
                "id=" + id +
                ", ourResult='" + ourResult + '\'' +
                ", upstreamResult='" + upstreamResult + '\'' +
                ", reason='" + reason + '\'' +
                ", solution='" + solution + '\'' +
                ", difficulty=" + difficulty +
                ", bits=" + bits +
                ", nonce='" + nonce + '\'' +
                '}';
    }
}