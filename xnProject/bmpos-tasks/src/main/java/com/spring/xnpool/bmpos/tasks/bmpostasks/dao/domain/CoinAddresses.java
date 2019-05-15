package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import java.math.BigDecimal;
import javax.persistence.*;

@Table(name = "coin_addresses")
public class CoinAddresses {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    @Column(name = "account_id")
    private Integer accountId;

    private String currency;

    @Column(name = "coin_address")
    private String coinAddress;

    @Column(name = "ap_threshold")
    private Float apThreshold;

    @Column(name = "reward_balance")
    private BigDecimal rewardBalance;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return account_id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * @param accountId
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * @return currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     * @param currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     * @return coin_address
     */
    public String getCoinAddress() {
        return coinAddress;
    }

    /**
     * @param coinAddress
     */
    public void setCoinAddress(String coinAddress) {
        this.coinAddress = coinAddress;
    }

    /**
     * @return ap_threshold
     */
    public Float getApThreshold() {
        return apThreshold;
    }

    /**
     * @param apThreshold
     */
    public void setApThreshold(Float apThreshold) {
        this.apThreshold = apThreshold;
    }

    /**
     * @return reward_balance
     */
    public BigDecimal getRewardBalance() {
        return rewardBalance;
    }

    /**
     * @param rewardBalance
     */
    public void setRewardBalance(BigDecimal rewardBalance) {
        this.rewardBalance = rewardBalance;
    }
}