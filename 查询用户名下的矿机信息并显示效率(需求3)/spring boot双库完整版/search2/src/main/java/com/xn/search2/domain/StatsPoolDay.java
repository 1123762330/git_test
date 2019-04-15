package com.xn.search2.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 
 */
public class StatsPoolDay implements Serializable {
    private Integer day;

    private Long shareAccept;

    private Long shareReject;

    private Double rejectRate;

    private BigDecimal score;

    private BigDecimal earn;

    private Double lucky;

    private Date createdAt;

    private Date updatedAt;

    private static final long serialVersionUID = 1L;

    public StatsPoolDay() {
    }

    public StatsPoolDay(Integer day, Long shareAccept, Long shareReject, Double rejectRate, BigDecimal score, BigDecimal earn, Double lucky, Date createdAt, Date updatedAt) {
        this.day = day;
        this.shareAccept = shareAccept;
        this.shareReject = shareReject;
        this.rejectRate = rejectRate;
        this.score = score;
        this.earn = earn;
        this.lucky = lucky;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
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

    public Double getLucky() {
        return lucky;
    }

    public void setLucky(Double lucky) {
        this.lucky = lucky;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", day=").append(day);
        sb.append(", shareAccept=").append(shareAccept);
        sb.append(", shareReject=").append(shareReject);
        sb.append(", rejectRate=").append(rejectRate);
        sb.append(", score=").append(score);
        sb.append(", earn=").append(earn);
        sb.append(", lucky=").append(lucky);
        sb.append(", createdAt=").append(createdAt);
        sb.append(", updatedAt=").append(updatedAt);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}