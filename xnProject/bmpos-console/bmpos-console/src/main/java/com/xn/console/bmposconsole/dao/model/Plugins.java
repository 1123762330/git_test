package com.xn.console.bmposconsole.dao.model;

import lombok.Data;

import java.io.Serializable;

/**
 * 插件实体类
 * @author Administrator
 *
 */
@Data
public class Plugins implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String pid;//"id": "xxxx", //插件唯一ID，英文字符
    private String category;//"category": "系统1", //属系统类
    private String name;//"name":"测试task", //插件名称
    private Integer active;//"active": 1, //是否激活（是否自动定时执行）1 表示激活
    private String unit;//"util":"H",   //定时间隔单位(N: 不支持定时执 S:秒 M:分钟 H:小时 D:天)
    private Integer duration;//"duration": 10, //定时间隔( util=H 为间隔10小时执行一次)
    private String start;//"start": "yyyy-mm-dd hh:mm" //第一次执行时间
	private String coin; //货币种类
    private String className;//插件执行类名放在pluginsExe包下
}
