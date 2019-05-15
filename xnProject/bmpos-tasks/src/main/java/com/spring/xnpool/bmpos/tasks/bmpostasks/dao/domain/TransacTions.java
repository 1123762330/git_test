package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

public class TransacTions {
    /**
     * 交易id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "SELECT LAST_INSERT_ID()")
    private Integer id;

    /**
     * 账目id
     */
    @Column(name = "account_id")
    private Integer accountId;

    /**
     * 类型
     */
    private String type;

    /**
     * 货币地址
     */
    @Column(name = "coin_address")
    private String coinAddress;

    /**
     * 数量
     */
    private BigDecimal amount;

    /**
     * 块ID
     */
    @Column(name = "block_id")
    private Integer blockId;

    private Date timestamp;

    /**
     * RPC事务标识符
     */
    private String txid;

    /**
     * 是否归档
     */
    private Boolean archived;

    /**
     * 获取交易id
     *
     * @return id - 交易id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置交易id
     *
     * @param id 交易id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取账目id
     *
     * @return account_id - 账目id
     */
    public Integer getAccountId() {
        return accountId;
    }

    /**
     * 设置账目id
     *
     * @param accountId 账目id
     */
    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    /**
     * 获取类型
     *
     * @return type - 类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型
     *
     * @param type 类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取货币地址
     *
     * @return coin_address - 货币地址
     */
    public String getCoinAddress() {
        return coinAddress;
    }

    /**
     * 设置货币地址
     *
     * @param coinAddress 货币地址
     */
    public void setCoinAddress(String coinAddress) {
        this.coinAddress = coinAddress;
    }

    /**
     * 获取数量
     *
     * @return amount - 数量
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * 设置数量
     *
     * @param amount 数量
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * 获取块ID
     *
     * @return block_id - 块ID
     */
    public Integer getBlockId() {
        return blockId;
    }

    /**
     * 设置块ID
     *
     * @param blockId 块ID
     */
    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    /**
     * @return timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 获取RPC事务标识符
     *
     * @return txid - RPC事务标识符
     */
    public String getTxid() {
        return txid;
    }

    /**
     * 设置RPC事务标识符
     *
     * @param txid RPC事务标识符
     */
    public void setTxid(String txid) {
        this.txid = txid;
    }

    /**
     * 获取是否归档
     *
     * @return archived - 是否归档
     */
    public Boolean getArchived() {
        return archived;
    }

    /**
     * 设置是否归档
     *
     * @param archived 是否归档
     */
    public void setArchived(Boolean archived) {
        this.archived = archived;
    }
}