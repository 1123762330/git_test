package com.xn.bmpos.api.bmposapi.domain.dao;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class MillInfoGroup implements Serializable {

    private static final long serialVersionUID = 6097848295070280477L;
    private Integer groupId;
    private String millName;
    private String oldName;
    private String groupName;
    private String accountName;
    private Map millInfo;


}
