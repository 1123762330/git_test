package com.wlk.domain;

import java.util.List;

public class WorkVO {
    private List userlist;
    private Long time;

    public WorkVO() {
    }

    public WorkVO(List userlist, Long time) {
        this.userlist = userlist;
        this.time = time;
    }

    public List getUserlist() {
        return userlist;
    }

    public void setUserlist(List userlist) {
        this.userlist = userlist;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "WorkVO{" +
                "userlist=" + userlist +
                ", time=" + time +
                '}';
    }
}
