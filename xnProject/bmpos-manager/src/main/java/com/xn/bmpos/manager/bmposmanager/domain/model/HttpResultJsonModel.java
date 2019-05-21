package com.xn.bmpos.manager.bmposmanager.domain.model;

import com.xn.bmpos.manager.bmposmanager.util.PageInfo;
import lombok.Data;

import java.util.Map;

@Data
public class HttpResultJsonModel<T> {
    private int state;

    private String resultCode;

    private String alertMessage;

    private T content;

    /**
     * 分页信息
     */
    private PageInfo page;

    /**
     * 其他内容
     */
    private Map<String, Object> ext;
}
