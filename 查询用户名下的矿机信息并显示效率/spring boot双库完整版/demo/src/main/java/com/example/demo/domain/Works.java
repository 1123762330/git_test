package com.example.demo.domain;

public class Works {
    private long worker_id;
    private int puid;
    private int group_id;
    private String worker_name;
    private long accept_1m;
    private long accept_5m;
    private long accept_15m;
    private long reject_15m;
    private long accept_1h;
    private long reject_1h;
    private int accept_count;
    private String last_share_ip;

    public Works() {
    }

    public Works(long worker_id, int puid, int group_id, String worker_name, long accept_1m, long accept_5m, long accept_15m, long reject_15m, long accept_1h, long reject_1h, int accept_count, String last_share_ip) {
        this.worker_id = worker_id;
        this.puid = puid;
        this.group_id = group_id;
        this.worker_name = worker_name;
        this.accept_1m = accept_1m;
        this.accept_5m = accept_5m;
        this.accept_15m = accept_15m;
        this.reject_15m = reject_15m;
        this.accept_1h = accept_1h;
        this.reject_1h = reject_1h;
        this.accept_count = accept_count;
        this.last_share_ip = last_share_ip;
    }

    public long getWorker_id() {
        return worker_id;
    }

    public void setWorker_id(long worker_id) {
        this.worker_id = worker_id;
    }

    public int getPuid() {
        return puid;
    }

    public void setPuid(int puid) {
        this.puid = puid;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getWorker_name() {
        return worker_name;
    }

    public void setWorker_name(String worker_name) {
        this.worker_name = worker_name;
    }

    public long getAccept_1m() {
        return accept_1m;
    }

    public void setAccept_1m(long accept_1m) {
        this.accept_1m = accept_1m;
    }

    public long getAccept_5m() {
        return accept_5m;
    }

    public void setAccept_5m(long accept_5m) {
        this.accept_5m = accept_5m;
    }

    public long getAccept_15m() {
        return accept_15m;
    }

    public void setAccept_15m(long accept_15m) {
        this.accept_15m = accept_15m;
    }

    public long getReject_15m() {
        return reject_15m;
    }

    public void setReject_15m(long reject_15m) {
        this.reject_15m = reject_15m;
    }

    public long getAccept_1h() {
        return accept_1h;
    }

    public void setAccept_1h(long accept_1h) {
        this.accept_1h = accept_1h;
    }

    public long getReject_1h() {
        return reject_1h;
    }

    public void setReject_1h(long reject_1h) {
        this.reject_1h = reject_1h;
    }

    public int getAccept_count() {
        return accept_count;
    }

    public void setAccept_count(int accept_count) {
        this.accept_count = accept_count;
    }

    public String getLast_share_ip() {
        return last_share_ip;
    }

    public void setLast_share_ip(String last_share_ip) {
        this.last_share_ip = last_share_ip;
    }

    @Override
    public String toString() {
        return "Works{" +
                "worker_id=" + worker_id +
                ", puid=" + puid +
                ", group_id=" + group_id +
                ", worker_name='" + worker_name + '\'' +
                ", accept_1m=" + accept_1m +
                ", accept_5m=" + accept_5m +
                ", accept_15m=" + accept_15m +
                ", reject_15m=" + reject_15m +
                ", accept_1h=" + accept_1h +
                ", reject_1h=" + reject_1h +
                ", accept_count=" + accept_count +
                ", last_share_ip='" + last_share_ip + '\'' +
                '}';
    }
}
