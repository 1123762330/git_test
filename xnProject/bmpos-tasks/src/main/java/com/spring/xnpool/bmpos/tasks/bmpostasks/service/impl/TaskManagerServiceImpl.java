package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;


import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.taskmanage.Plugins;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.ITaskManagerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class TaskManagerServiceImpl implements ITaskManagerService {
    private Logger log = LoggerFactory.getLogger(TaskManagerServiceImpl.class);
    private HashMap<String, Plugins> pMap = new HashMap<>();

    public HashMap<String, Plugins> getpMap() {
        //注册插件
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Plugins plugins = new Plugins();
        plugins.setPid(DAILY_PLUGINS_ID);
        plugins.setClassName("IDailyService");
        plugins.setActive(1);
        plugins.setUnit("M");
        plugins.setDuration(20);
        plugins.setName("日结");
        plugins.setCategory("插件");
        plugins.setCoin("ltc");
        pMap.put(plugins.getPid(), plugins);

        //注册插件
        Plugins plugins1 = new Plugins();
        plugins1.setPid(PAYOUT_PLUGINS_ID);
        plugins1.setClassName("IPayOutService");
        plugins1.setActive(1);
        plugins1.setUnit("M");
        plugins1.setDuration(30);
        plugins1.setName("支付");
        plugins1.setCategory("插件");
        plugins1.setStart(simpleDateFormat.format(new Date()));
        plugins1.setCoin("ltc");
        pMap.put(plugins1.getPid(), plugins1);

        //注册插件完毕
        Plugins plugins2 = new Plugins();
        plugins2.setPid(PPS_PAYOUT_PLUGINS_ID);
        plugins2.setClassName("pps_payout");
        plugins2.setActive(1);
        plugins2.setUnit("M");
        plugins2.setDuration(5);
        plugins2.setName("结算");
        plugins2.setCategory("插件");
        plugins2.setCoin("ltc");
        plugins2.setStart(simpleDateFormat.format(new Date()));
        pMap.put(plugins2.getPid(), plugins2);

        //注册插件完毕
        Plugins plugins3 = new Plugins();
        plugins3.setPid(SUM_DATA_ID);
        plugins3.setClassName("SummarizedData");
        plugins3.setActive(1);
        plugins3.setUnit("M");
        plugins3.setDuration(5);
        plugins3.setName("数据统计");
        plugins3.setCategory("插件");
        plugins3.setCoin("ltc");
        plugins3.setStart(simpleDateFormat.format(new Date()));
        pMap.put(plugins3.getPid(), plugins3);
        return pMap;

    }


}
