package com.xnpool.account.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Impower implements Serializable {
    private static final long serialVersionUID = 9147544761970284923L;
    private Integer id;
    private Integer userId;//用户id
    private Integer impowerAid;//授权子账户id
    private String impowerTime;//授权时限
    private Integer isExpiry;//是否失效 0为未失效，1为失效
    private Date startTime;//开始时间
    private String byImpowerName;//被授权用户名
    private String authority;//读写的权限
    private String remark;//授权记录
}
