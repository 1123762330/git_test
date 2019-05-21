package com.spring.xnpool.bmpos.tasks.bmpostasks.taskmanage;


import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.taskmanage.Plugins;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.*;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.AccountAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.RewardAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.feign.WalletProxyAPI;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IDailyService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IPayOutService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.ITaskManagerService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.SummarizedData;
import com.spring.xnpool.bmpos.tasks.bmpostasks.taskSettlement.TaskPayout;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.NullPointerException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;


@Service
public class ScheduledForDynamicCron implements SchedulingConfigurer, ApplicationRunner {

    private Logger log = LoggerFactory.getLogger(ScheduledForDynamicCron.class);

    @Autowired
    WalletProxyAPI walletProxyAPI;
    @Autowired
    AccountAPI accountAPI;
    @Autowired
    RewardAPI rewardAPI;
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;
    @Value("${myprops.filePath}")
    private String filePath;
    private ScheduledFuture<?> future;
    private static Map<String, ScheduledFuture<?>> threadMap = new ConcurrentHashMap<>();
    public static HashMap<String, Plugins> pmap = new HashMap<>();
    @Autowired
    SummarizedData summarizedData;
    @Bean
    public ThreadPoolTaskScheduler threadPoolTaskScheduler() {
        return new ThreadPoolTaskScheduler();
    }

    @Value("${myprops.time}")
    private String timeCrom;
    private static final String DEFAULT_CRON = "0/10 * * * * ?";
    private String cron = DEFAULT_CRON;

    @Autowired
    private ITaskManagerService taskManger;
    @Autowired
    IDailyService iDailyService;
    @Autowired
    IPayOutService iPayOutService;
    @Autowired
    TaskPayout taskPayout;


    /**
     * map初始化
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("插件Map开始初始化！");
        pmap = taskManger.getpMap();
        log.info("插件Map:" + pmap);
        log.error("插件Map开始初始化完毕！");
    }

    /**
     * 多线程定时任务方法
     *
     * @param taskRegistrar
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addTriggerTask(() -> {
            // 定时任务的业务逻辑
            // 从map中取得开启状态的所有任务，与已经定时的任务做比较，新的任务开启新任务
            // 传入参数：开始时间 结束时间
            if (pmap == null || pmap.size() == 0) {
                pmap = readPmap();
            }
            //todo 测试
            //JSONObject dataByAddress = accountAPI.findNewName("23X57","ltc");
            //System.err.println("getByAidAndCurrency:"+ dataByAddress);
            pmap.forEach((key, plugins) -> {
                if (plugins.getActive() == 0) {// 0表示未激活
                    stopThread(plugins.getPid());
                } else if (threadMap.get(plugins.getPid()) == null) {
                    log.info(" === " + plugins.getName() + "插件正在定时 === ");
                    cron = checkCron(plugins.getUnit(), plugins.getDuration());
                    future = threadPoolTaskScheduler.schedule(new MyRunnable(plugins.getPid() + "", plugins.getCoin()), new CronTrigger(cron));
                    threadMap.put(plugins.getPid(), future);
                }
            });
        }, (triggerContext) -> {
            // 定时任务触发，可修改定时任务的执行周期
            CronTrigger trigger = new CronTrigger(timeCrom);
            Date nextExecDate = trigger.nextExecutionTime(triggerContext);
            return nextExecDate;
        });
    }

    /**
     * 停止指定线程
     *
     * @param pid
     * @return
     */
    public boolean stopThread(String pid) {

        Plugins plugins = findPmapBy(pid);
        if (plugins == null) {
            throw new DataNotFoundException("插件未找到！");
        }
        future = threadMap.get(pid);
        if (future != null) {
            plugins.setActive(0);
            pmap.replace(pid, plugins);
            //taskManger.stopPlugins(pid,plugins.getName());
            future.cancel(true);
            return true;
        }
        return false;
    }

    /**
     * 设置定时扫描jar包文件的间隔
     *
     * @param unit
     * @param duration
     */
    public void setTimeCron(String unit, Integer duration) {
        timeCrom = checkCron(unit, duration);
    }

    /**
     * 设置定时时间
     *
     * @param pid
     * @param unit
     * @param duration
     */
    public void setThread(String pid, String unit, Integer duration) {
        log.info("====== 设置插件Map参数前：=====" + pmap);
        Plugins plugins = findPmapBy(pid);
        if (plugins == null) {
            throw new DataNotFoundException("插件未找到！");
        }
        if (plugins.getActive() == 0) {
            throw new NotActiveException("该插件未激活");
        }
        if ("N".equals(unit)) {
            throw new NotSupportException("定时时间不能为N，N表示不支持定时！");
        }
        plugins.setUnit(unit);
        plugins.setDuration(duration);
        pmap.replace(pid, plugins);
        //stopThread(pid);
        String cron = checkCron(unit, duration);
        future = threadPoolTaskScheduler.schedule(new MyRunnable(plugins.getPid() + "", plugins.getCoin()),
                new CronTrigger(cron));
        threadMap.put(plugins.getPid(), future);
        log.info("====== 设置插件Map参数后：=====" + pmap);
        savePmap();
    }


    /**
     * 返回插件列表
     *
     * @return
     */
    public List<Plugins> getList() {
        List<Plugins> plugins = new ArrayList<>();
        if (pmap != null) {
            pmap.forEach((key, p) -> {
                plugins.add(p);
            });
        }
        return plugins;
    }

    /**
     * 启动线程任务
     *
     * @param pid
     */
    public void startThread(String pid, String unit, Integer duration) {
        Plugins plugins = findPmapBy(pid);
        if (plugins == null) {
            throw new DataNotFoundException(plugins.getName() + "插件未找到！");
        }
        if (plugins.getActive() == 1) {
            throw new ActiveException(plugins.getName() + "插件已启动");
        }
        plugins.setActive(1);
        //String unit = plugins.getUnit();
        //Integer duration = plugins.getDuration();
        plugins.setUnit(unit);
        plugins.setDuration(duration);
        pmap.replace(pid, plugins);
        //stopThread(pid);
        String cron = checkCron(unit, duration);

        future = threadPoolTaskScheduler.schedule(new MyRunnable(plugins.getPid() + "",
                        plugins.getCoin()),
                new CronTrigger(cron));
        threadMap.put(plugins.getPid(), future);

        log.info("插件" + plugins.getName() + "启动了！");
        savePmap();
    }

    /**
     * 设置激活状态
     *
     * @param pid
     */
    public Integer setActive(String pid) {
        Plugins plugins = findPmapBy(pid);
        if (plugins == null) {
            throw new DataNotFoundException("插件未找到！");
        }
        if (plugins.getActive() == 0) {
            plugins.setActive(1);
        } else {
            plugins.setActive(0);
        }
        pmap.replace(pid, plugins);
        log.info("==========激活状态已改变==========");
        return plugins.getActive();
    }

    public Plugins findPmapBy(String id) {
        return pmap.get(id);
    }

    /**
     * 手动执行插件执行接口
     *
     * @param pid
     */
    public void exePlugins(String pid) {
        Plugins plugins = findPmapBy(pid);
        if (plugins == null) {
            throw new DataNotFoundException(plugins.getName() + "插件未找到！");
        }
        if (plugins.getActive() == 1) {
            throw new ActiveException(plugins.getName() + "插件已激活！");
        }
        //手动执行get请求
        String urlexe = plugins.getClassName();
        log.info("执行请求地址:" + urlexe);
        //todo 手动
        if ("IDailyService".equals(urlexe)) {
            ///iDailyService.dailyExe();
        }
    }

    public void setPlugins(Plugins plugins) {
        Plugins data = findPmapBy(plugins.getPid());
        if (data == null) {
            throw new DataNotFoundException(plugins.getName() + "插件未找到！");
        }
        if (plugins.getActive() == null) {
            plugins.setActive(data.getActive());
        }
        if (plugins.getName() == null) {
            plugins.setName(data.getName());
        }
        if (plugins.getDuration() == null) {
            plugins.setDuration(data.getDuration());
        }
        if (plugins.getUnit() == null) {
            plugins.setUnit(data.getUnit());
        }
        if (plugins.getClassName() == null) {
            plugins.setClassName(data.getClassName());
        }
        if (plugins.getCoin() == null) {
            plugins.setCoin(data.getCoin());
        }
        if (plugins.getCategory() == null) {
            plugins.setCategory(data.getCategory());
        }
        pmap.replace(plugins.getPid(), plugins);

         /*  if ("IDailyService".equals(urlexe)) {
            iDailyService.dailyExe();
        }*/
    }


    private class MyRunnable implements Runnable {
        private String pid = "";
        private String coin = "";

        public MyRunnable(String pid, String coin) {
            this.pid = pid;
            this.coin = coin;
        }

        @Override
        public void run() {
            // 执行插件业务

            Plugins plugins = findPmapBy(pid);
            if (plugins == null) {
                throw new DataNotFoundException(plugins.getName() + "插件未找到！");
            }
            log.info("执行插件业务" + plugins.getName());
            //执行请求
            String urlexe = plugins.getClassName();
            if("SummarizedData".equals(urlexe)){
                try {
                    summarizedData.sumDate(coin);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            if ("IDailyService".equals(urlexe)) {
                iDailyService.dailyExe(coin);
            }

            if ("IPayOutService".equals(urlexe)) {
                    iPayOutService.PayOut(coin);
            }
            if ("pps_payout".equals(urlexe)) {

                try {
                    taskPayout.tasks_pps_payOut(coin);
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            }
            if ("H".equals(plugins.getUnit()) || "D".equals(plugins.getUnit())) {
                //停止
                boolean result1 = stopThread(pid);
            }
        }
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

    /**
     * 备份插件pmap
     */
    public void savePmap() {
        try {
            //创建文件
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }

            if (pmap != null && pmap.size() != 0) {
                //保存数据
                Properties prop = new Properties();
                log.info("要备份的插件Map:" + pmap);
                pmap.forEach((key, plugins) -> {
                    prop.setProperty(plugins.getPid(), plugins.getCategory() + "&" + plugins.getName() + "&" + plugins.getActive() + "&" + plugins.getUnit() + "&" + plugins.getDuration() + "&" + plugins.getStart());
                });
                //prop.list(System.out);
                FileOutputStream fos = new FileOutputStream(file);
                prop.store(fos, "pmap");
                log.info("========== 插件Map已备份！==========");
                //读取数据
			/*prop=new Properties();
			FileInputStream fis=new FileInputStream(file);
			prop.load(fis);
			prop.list(System.out);*/
            }
        } catch (Exception e) {
            throw new ServiceException(e.getClass() + " " + e.getMessage());
        }
    }

    /**
     * 读取备份插件pmap
     */
    public HashMap<String, Plugins> readPmap() {
        HashMap<String, Plugins> pluginsHashMap = new HashMap<>();
        //创建文件
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.createNewFile();
            }
            Properties prop = new Properties();
            FileInputStream fis = new FileInputStream(file);
            prop.load(fis);
            Set props = prop.keySet();
			/* if(props.size()==0){
			 	throw new DataNotFoundException("还未备份数据！");
			 }*/
            for (int i = 0; i < props.size(); i++) {
                String key = props.toArray()[i].toString();
                System.out.println("key:" + key);
                Plugins plugins = new Plugins();
                String pluginsStr = prop.getProperty(key);
                System.out.println("pluginsStr:" + pluginsStr);
                String[] subStrs = pluginsStr.split("&");
                System.out.println(Arrays.toString(subStrs));
                plugins.setPid(key);
                plugins.setCategory(subStrs[0]);
                plugins.setName(subStrs[1]);
                plugins.setActive(Integer.valueOf(subStrs[2]));
                plugins.setUnit(subStrs[3]);
                plugins.setDuration(Integer.valueOf(subStrs[4]));
                plugins.setStart(subStrs[5]);
                pluginsHashMap.put(key, plugins);
            }
            log.info("===调用备份插件Map====:" + pluginsHashMap);
        } catch (IOException e) {
            throw new ServiceException(e.toString() + " " + e.getMessage());
        }
        return pluginsHashMap;
    }
}
