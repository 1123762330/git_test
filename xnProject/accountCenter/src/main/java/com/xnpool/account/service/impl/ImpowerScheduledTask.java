package com.xnpool.account.service.impl;

import com.xnpool.account.entity.Impower;
import com.xnpool.account.service.ImpowerService;
import com.xnpool.account.service.exception.NotSupportException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

@Service
@Slf4j
public class ImpowerScheduledTask implements SchedulingConfigurer {
    private static final String DEFAULT_CRON = "0/10 * * * * ?";
    private String timeCron = DEFAULT_CRON;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    private static Map<String, ScheduledFuture<?>> threadMap = new ConcurrentHashMap<>();
    private ScheduledFuture<?> future;
    @Autowired
    private ImpowerService impowerService;
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            // 定时任务的业务逻辑
            // 从map中取得开启状态的所有任务，与已经定时的任务做比较，新的任务开启新任务
            // 传入参数：开始时间 结束时间
            //todo 测试
            //System.err.println("设置定时时间：");
            List<Impower> impowers = impowerService.selectTime();
            if(!impowers.isEmpty()){
                impowers.forEach((impower)->{
                    Integer id = impower.getId();
                    String impowerTime = impower.getImpowerTime();
                    String duration = impowerTime.substring(0, impowerTime.length() - 1);
                    String unit = impowerTime.substring(impowerTime.length() - 1);
                    Integer impowerAid = impower.getImpowerAid();
                     String cron = checkCron(unit,Integer.valueOf(duration) );//unit 单位 duration 时间间隔
                    log.info("开始线程：子账户号："+impowerAid+"，授权id："+id);
                    future = threadPoolTaskScheduler.schedule(new MyRunnable(id.toString()),  new CronTrigger(cron));
                    threadMap.put(id.toString(), future);
                });
            }else {
                return;
            }

        }, (triggerContext) -> {
            // 定时任务触发，可修改定时任务的执行周期
            CronTrigger trigger = new CronTrigger(timeCron);
            Date nextExecDate = trigger.nextExecutionTime(triggerContext);
            return nextExecDate;
        });
    }

    private class MyRunnable implements Runnable {
        private String id = "";
        public MyRunnable(String id) {
            this.id = id;
        }

        @Override
        public void run() {
            // 执行业务
           log.info("执行业务：");
            impowerService.updateAuthorising(Integer.valueOf(id));
            //执行完毕停止线程
            stopThread(id);
           log.info("停止线程：授权id："+id+"結束時間");
        }
    }

    /**
     * 停止指定线程
     *
     * @param id
     * @return
     */
    public boolean stopThread(String id) {
        future = threadMap.get(id);
        if (future != null) {
            future.cancel(true);
            return true;
        }
        return false;
    }
    private String checkCron(String util, Integer duration) {
        String cron = null;
        if ("".equals(util) || util == null) {
            throw new NullPointerException("定时单位不能为空！");
        }
        if (duration == null) {
            throw new NullPointerException("定时间隔不能为空！");
        }
        if ("N".equals(util)) {
            log.error("该插件不支持定时！");
            throw new NotSupportException("该插件不支持定时！");
        }
        if ("S".equals(util)) {
            cron = "0/S * * * * *";
            cron = cron.replace("S", duration.toString());

        }
        if ("M".equals(util)) {
            log.info("定时时间为:" + duration + util);
            cron = "0 0/M * * * ? ";
            cron = cron.replace("M", duration.toString());
            //log.info("cron:" + cron);
        }
        if ("H".equals(util)) {
            log.info("定时时间为:" + duration + util);
            cron = "0 0 0/H  * * ? ";
            cron = cron.replace("H", duration.toString());
        }
        if ("D".equals(util)) {
            log.info("定时时间为:" + duration + util);
            cron = "0 0 0 0/D * ? ";
            cron = cron.replace("D", duration.toString());
        }
        return cron;
    }
}
