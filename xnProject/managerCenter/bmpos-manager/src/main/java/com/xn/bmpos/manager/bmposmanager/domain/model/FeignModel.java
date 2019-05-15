package com.xn.bmpos.manager.bmposmanager.domain.model;

import com.xn.bmpos.manager.bmposmanager.util.PageInfo;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class FeignModel<T> implements Serializable {
    /**
     * 返回状态码
     */
    private String status;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 返回内容
     */
    private T data;

    /**
     * 分页信息
     */
    private PageInfo page;

    /**
     * 其他内容
     */
    private Map<String, Object> ext;



    private boolean success;

}
