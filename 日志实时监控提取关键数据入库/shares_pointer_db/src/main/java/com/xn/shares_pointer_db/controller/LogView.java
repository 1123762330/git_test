package com.xn.shares_pointer_db.controller;

import com.xn.shares_pointer_db.dao.impl.SharesDaoImpl;
import com.xn.shares_pointer_db.domain.Shares;
import com.xn.shares_pointer_db.service.IRedisService;
import com.xn.shares_pointer_db.service.RedisService;
import com.xn.shares_pointer_db.utils.BufferedRandomAccessFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@Order(value = 1)//如果有多个启动程序,按照序号顺序执行
@EnableScheduling
public class LogView implements CommandLineRunner {
    @Value("${log_path_prefix}")
    private String path;

    @Autowired
    private IRedisService service;

    //@Autowired
    //private RedisUtils redisUtils;

    @Autowired
    private RedisService redisService;


    private long pointer = 0; //上次文件大小
    private int redispointer = 0;
    private static final Logger logger = LoggerFactory.getLogger(LogView.class);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
    //启动一个线程每10秒钟读取新增的日志信息
    ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);

    @Override
    public void run(String... args) throws Exception {

        readLogDir(new File(path));//开启程序
    }

    //开启线程
    public void realtimeShowLog(File logFile) throws IOException {
        if (logFile == null) {
            throw new IllegalStateException("文件没有被找到");
        }
        exec.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                readLog(logFile);
            }
        }, 0, 10, TimeUnit.SECONDS);

        /*exec.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                long start = new Date().getTime();//程序开始时间
                logger.info("开始时间是" + start);
                saveLog();
                long end = new Date().getTime();
                System.out.println("结束时间是" + end);
                long time = end - start;
                System.out.println("总耗时" + time);
            }
        }, 2, 15, TimeUnit.SECONDS);*/


    }

    //日志读取入缓存方法
    public void readLog(File logFile) {
        LinkedHashMap<String, Shares> map = new LinkedHashMap<>();
        SharesDaoImpl sharesDao = new SharesDaoImpl();
        try {
            long len = logFile.length();
            if (len < pointer) {
                System.out.println("日志文件被重置。从文件开始重新启动日志记录。");
                pointer = 0;
            } else {
                //指定文件可读可写
                BufferedRandomAccessFile randomFile = new BufferedRandomAccessFile(logFile, "rw");

                //LineNumberReader randomFile=new LineNumberReader(new FileReader("F:\\workspace\\testworks\\boot\\shares_pointer\\src\\main\\resources\\grin-server.log"));
                //获取RandomAccessFile对象文件指针的位置，初始位置是0
                System.out.println("RandomAccessFile文件指针的初始位置:" + pointer);

                randomFile.seek(pointer);//移动文件指针位置

                String tmp = "";
                int count = 0;
                Shares shares = new Shares();

                long start = new Date().getTime();//程序开始时间
               //logger.info("入缓存开始时间是" + start);
                while ((tmp = randomFile.readLine()) != null) {
                    if (tmp.contains("Got share")) {
                        count++;
                        stringData(tmp, shares, count);//对关键数据进行字符串切割
                        try {
                            service.put(shares.getNonce(), shares, 1 * 3600 * 24);//设置24小时候过期
                            Shares shares1 = (Shares) service.get(shares.getNonce());
                            map.put(shares.getNonce(),shares1);
                            System.out.println("存入缓存成功!");
                            //System.out.println("指针===="+pointer);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    pointer = randomFile.getFilePointer();//文件指针移动的位置
                }
                randomFile.close();
                long end = new Date().getTime();
                //System.out.println("结束时间是" + end);
                long time = end - start;
                System.out.println("入缓存总耗时" + time);


                if(map.size()!=0){
                    ArrayList<Shares> list = new ArrayList<>();
                    Iterator iter = map.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry entry = (Map.Entry) iter.next();
                        //String key = (String) entry.getKey();
                        Shares val = (Shares) entry.getValue();
                        list.add(val);
                    }

                    long start1 = new Date().getTime();//程序开始时间

                    try {
                        sharesDao.insertAll(list);//将值入库
                        System.out.println("成功入库");
                    } catch (Exception e) {
                        //e.printStackTrace();
                        logger.info("入库失败,请检查!");
                    }

                    //logger.info("入库开始时间是" + start);
                    long end1 = new Date().getTime();
                    //System.out.println("结束时间是" + end);
                    long time1 = end - start;
                    System.out.println("入库总耗时" + time);

                }

            }
        } catch (IOException e) {
            //实时读取日志异常，需要记录时间和lastTimeFileSize 以便后期手动补充
            System.out.println(dateFormat.format(new Date()) + "文件读取错误,最后指针的位置:" + pointer);
        } finally {
            //将lastTimeFileSize 落地以便下次启动的时候，直接从指定位置获取
            logger.info("程序即将开启下一次扫描");
        }
    }

    //关键数据提取
    public void stringData(String tmp, Shares shares, int count) {
        //logger.info("线程是" + Thread.currentThread().getName());
        logger.info("找到的字符串:" + tmp + "在第" + count);
        String height = tmp.substring(tmp.indexOf("height") + 6, tmp.indexOf("with")).trim();
        String nonce = tmp.substring(tmp.indexOf("nonce") + 5, tmp.indexOf("with difficulty")).trim();
        String difficulty = tmp.substring(tmp.indexOf("difficulty") + 11, tmp.indexOf("from")).trim();
        int difficu = Integer.parseInt(difficulty);
        //根据work关键字截取新的字符串
        String str = tmp.substring(tmp.indexOf("worker") + 6).trim();
        String workerid = str.substring(0, str.indexOf("-")).trim();
        int workerId = Integer.parseInt(workerid);
        String message_id = str.substring(str.indexOf("-") + 1).trim();
        String time = tmp.substring(0, tmp.indexOf("WARN")).trim();
        shares.setDifficulty(difficu);
        shares.setHeight(height);
        shares.setNonce(nonce);
        shares.setTime(time);
        shares.setWorkerId(workerId);
    }

    //从缓存中将数据入库
    public void saveLog() {
        int si = 0;
        SharesDaoImpl sharesDao = new SharesDaoImpl();
        Shares shares = new Shares();
        //数据从缓存中入库
        ZSetOperations keys = service.getKeys();//获取键集合
//List<Map> keyTest = redisService.hashget("key_test");
        ArrayList<Shares> list = new ArrayList<>();

        System.out.println("===================="+keys);
        /*Iterator ite = keys.iterator();
        System.out.println("从缓存中取出数据!");


        while(ite.hasNext()) {
            String key = (String) ite.next();
            if (si == redispointer) {
                redispointer++;
                System.out.println("第"+redispointer+"key:"+key);
                Shares value= (Shares) service.get(key);
                //System.out.println("最新的值"+value);
                //list.add(value);
            }
            si++;

        }*/
        System.out.println("新的元素个数"+list.size());

        for (int i = 0; i < list.size(); i++) {
            System.out.println("新的元素"+list.get(i).getNonce());
        }

        try {
            //sharesDao.insertAll(list);//将值入库
            list.clear();
            System.out.println("入库之后的"+list);
            logger.info("成功入库!!!");
        } catch (Exception e) {
            //e.printStackTrace();
            logger.info("数据已存在,入库失败");
        }
    }

    public void stop() {
        if (exec != null) {
            exec.shutdown();
            logger.info("file read stop ！");
        }
    }

    private void readLogDir(File fileDir) throws Exception {
        String fileName = "";
        if (fileDir != null) {
            File[] files = fileDir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".log")) {
                        fileName = file.getName();//文件名
                        String fileUrl = fileDir + "\\" + fileName;//文件路径拼接
                        File logFile = new File(fileUrl);
                        realtimeShowLog(logFile);
                    } else {
                        logger.info("不是日志文件,继续查找下个文件");
                    }
                } else {
                    logger.info("目录下没有文件!");
                }
            }
        } else {
            logger.info("文件夹是空的");
        }
    }
}