package com.xn.search2.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class StatsUsersHour implements Serializable {
    private Integer puid;

    private Integer hour;

    private Long shareAccept;

    private Long shareReject;

    private Double rejectRate;

    private BigDecimal score;

    private BigDecimal earn;

    private Date createdAt;

    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    public Integer getPuid() {
        return puid;
    }

    public void setPuid(Integer puid) {
        this.puid = puid;
    }

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Long getShareAccept() {
        return shareAccept;
    }

    public void setShareAccept(Long shareAccept) {
        this.shareAccept = shareAccept;
    }

    public Long getShareReject() {
        return shareReject;
    }

    public void setShareReject(Long shareReject) {
        this.shareReject = shareReject;
    }

    public Double getRejectRate() {
        return rejectRate;
    }

    public void setRejectRate(Double rejectRate) {
        this.rejectRate = rejectRate;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getEarn() {
        return earn;
    }

    public void setEarn(BigDecimal earn) {
        this.earn = earn;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public StatsUsersHour() {
    }

    public StatsUsersHour(Integer puid, Integer hour, Long shareAccept, Long shareReject, Double rejectRate, BigDecimal score, BigDecimal earn, Date createdAt, Date updatedAt) {
        this.puid = puid;
        this.hour = hour;
        this.shareAccept = shareAccept;
        this.shareReject = shareReject;
        this.rejectRate = rejectRate;
        this.score = score;
        this.earn = earn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "StatsUsersHour{" +
                "puid=" + puid +
                ", hour=" + hour +
                ", shareAccept=" + shareAccept +
                ", shareReject=" + shareReject +
                ", rejectRate=" + rejectRate +
                ", score=" + score +
                ", earn=" + earn +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}