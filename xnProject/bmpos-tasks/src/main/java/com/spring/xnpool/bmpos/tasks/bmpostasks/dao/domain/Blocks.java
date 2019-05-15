package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import javax.persistence.*;

public class Blocks {
    /**
     * 块ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 高
     */
    private Integer height;

    /**
     * 哈希块
     */
    private String blockhash;

    /**
     * 证实
     */
    private Integer confirmations;

    /**
     * 数量
     */
    private Double amount;

    /**
     * 难度值
     */
    private Double difficulty;

    private Integer time;

    /**
     * 是否考虑
     */
    private Boolean accounted;

    /**
     * 账户ID
     */
    @Column(name = "account_id")
    private Integer accountId;

    /**
     * 工作者
     */
    @Column(name = "worker_name")
    private String workerName;

    /**
     * 分享计数
     */
    private Long shares;

    /**
     * 上游有效共享ID
     */
    @Column(name = "share_id")
    private Long shareId;

    /**
     * 获取块ID
     *
     * @return id - 块ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置块ID
     *
     * @param id 块ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取高
     *
     * @return height - 高
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * 设置高
     *
     * @param height 高
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * 获取哈希块
     *
     * @return blockhash - 哈希块
     */
    public String getBlockhash() {
        return blockhash;
    }

    /**
     * 设置哈希块
     *
     * @param blockhash 哈希块
     */
    public void setBlockhash(String blockhash) {
        this.blockhash = blockhash;
    }

    /**
     * 获取证实
     *
     * @return confirmations - 证实
     */
    public Integer getConfirmations() {
        return confirmations;
    }

    /**
     * 设置证实
     *
     * @param confirmations 证实
     */
    public void setConfirmations(Integer confirmations) {
        this.confirmations = confirmations;
    }

    /**
     * 获取数量
     *
     * @return amount - 数量
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * 设置数量
     *
     * @param amount 数量
     */
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    /**
     * 获取难度值
     *
     * @return difficulty - 难度值
     */
    public Double getDifficulty() {
        return difficulty;
    }

    /**
     * 设置难度值
     *
     * @param difficulty 难度值
     */
    public void setDifficulty(Double difficulty) {
        this.difficulty = difficulty;
    }

    /**
     * @return time
     */
    public Integer getTime() {
        return time;
    }

    /**
     * @param time
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * 获取是否考虑
     *
     * @return accounted - 是否考虑
     */
    public Boolean getAccounted() {
        return accounted;
    }

    /**
     * 设置是否考虑
     *
     * @param accounted 是否考虑
     */
    public void setAccounted(Boolean accounted) {
        this.accounted = accounted;
    }

    /**
     * 获取账户ID
     *
     * @return account_id - 账户ID
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 设置账户ID
     *
     * @param accountId 账户ID
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取工作者
     *
     * @return worker_name - 工作者
     */
    public String getWorkerName() {
        return workerName;
    }

    /**
     * 设置工作者
     *
     * @param workerName 工作者
     */
    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    /**
     * 获取分享计数
     *
     * @return shares - 分享计数
     */
    public Long getShares() {
        return shares;
    }

    /**
     * 设置分享计数
     *
     * @param shares 分享计数
     */
    public void setShares(Long shares) {
        this.shares = shares;
    }

    /**
     * 获取上游有效共享ID
     *
     * @return share_id - 上游有效共享ID
     */
    public Long getShareId() {
        return shareId;
    }

    /**
     * 设置上游有效共享ID
     *
     * @param shareId 上游有效共享ID
     */
    public void setShareId(Long shareId) {
        this.shareId = shareId;
    }
}