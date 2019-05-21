package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain;

import java.io.Serializable;

public class MessageBeens implements Serializable {
    private static final long serialVersionUID = -4845675519015700894L;
    private  String coin;
    private  String address;
    //离线矿机名称
    private  String offlinename;
    //离线矿机数量
    private  String offlinenum;

    //恢复矿机名称
    private  String onlinename;

    //支付
    //订单号
    private  String tx;
    //金额
    private  String amount;

    // 时间
    private String time;

    public String getCoin() {
        return coin;
    }

    public void setCoin(String coin) {
        this.coin = coin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getOfflinename() {
        return offlinename;
    }

    public void setOfflinename(String offlinename) {
        this.offlinename = offlinename;
    }

    public String getOfflinenum() {
        return offlinenum;
    }

    public void setOfflinenum(String offlinenum) {
        this.offlinenum = offlinenum;
    }

    public String getOnlinename() {
        return onlinename;
    }

    public void setOnlinename(String onlinename) {
        this.onlinename = onlinename;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}