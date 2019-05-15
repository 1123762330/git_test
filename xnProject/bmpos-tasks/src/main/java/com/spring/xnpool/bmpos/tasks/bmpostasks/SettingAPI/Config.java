package com.spring.xnpool.bmpos.tasks.bmpostasks.SettingAPI;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Config implements Serializable {
    private static final long serialVersionUID = 3504006443711662435L;
    private String name;//配置名key
    private String value;//配置值
    private String remark;//备注
    private Date time;//时间
    private Integer id;
}
