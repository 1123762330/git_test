package com.xnpool.account.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MillName {
    private Integer millId;//矿机id
    private Integer groupId;//分组ID
    private String millName;//矿机新名字
    private String oldName;//矿机老名字
    private String groupName;//分组名
    private String accountName;
}
