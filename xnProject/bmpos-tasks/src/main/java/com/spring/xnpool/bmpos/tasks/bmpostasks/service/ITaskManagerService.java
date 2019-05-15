package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.taskmanage.Plugins;


import java.util.HashMap;

public interface ITaskManagerService {
	String PPS_PAYOUT_PLUGINS_ID = "1";
	String DAILY_PLUGINS_ID = "2";
	String PAYOUT_PLUGINS_ID = "3";
	String SUM_DATA_ID = "4";
	/**
	 * 通过文件初始化插件
	 * @param
	 * @return
	 */
	HashMap<String,Plugins> getpMap();
}
