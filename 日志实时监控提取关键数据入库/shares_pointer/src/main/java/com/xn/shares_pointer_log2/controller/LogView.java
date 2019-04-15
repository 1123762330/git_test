package com.xn.shares_pointer_log2.controller;

import com.xn.shares_pointer_log2.dao.impl.SharesDaoImpl;
import com.xn.shares_pointer_log2.domain.Shares;
import com.xn.shares_pointer_log2.service.IRedisService;
import com.xn.shares_pointer_log2.utils.BufferedRandomAccessFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class LogView implements CommandLineRunner {
    @Value("${log_path_prefix}")
    private String path;

    @Autowired
    private IRedisService service;

    private long pointer;//文件读取指针
    private String pointer_str;//缓存中记录的每次读取的最后一条数据
    //private int strCount;//每次日志文件更新,只读取第一行数据做判断,这个为计数器
    private String copyTemp;//文件每次读取到最后一行,将整行数据赋值给它,并添加到缓存中
    private long oldPointer;//指针回滚到最后一天数据之前,方便程序读取最后一条数据
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss", java.util.Locale.ENGLISH);
    private static final Logger logger = LoggerFactory.getLogger(LogView.class);
    StringBuffer buf = new StringBuffer();
    ScheduledExecutorService exec = Executors.newScheduledThreadPool(2);//启动一个线程每10秒钟读取新增的日志信息

    //程序启动入口
    @Override
    public void run(String... args) throws Exception {
        boolean hKeyExists = service.isHKeyExists("pointers", "point");//首先判断指针键有没有
        if (!hKeyExists) {//如果不存在,就赋值为0
            service.hput("pointers", "point", 0L);
            logger.info("指针不存在,将指针重置为0,并加入缓存!");
            pointer = 0;
        } else {//如果存在就直接取值拿到指针
            pointer = (Long) service.hget("pointers", "point");
            logger.info("启动时从redis中获取的缓存指针是" + pointer);
            pointer_str = (String) service.hget("pointers", "pointer_str");
            logger.info("启动时获取上次最后读取的字符串是:" + pointer_str);
        }

        readLogDir(new File(path));//开启程序
    }

    //开启多线程
    public void realtimeShowLog(File logFile) throws IOException {
        if (logFile == null) {
            throw new IllegalStateException("文件没有被找到");
        }

        exec.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                //strCount = 0;
                //service.hput("pointers", "point", 249112277L);//将每次读取的最终指针加入到缓存中
                //service.hput("pointers", "pointer_str", "Apr 11 12:01:29.110 DEBG Grin Pool - broadcasting a job to 1 workers");//将每次读取的最后一条数据存入到缓存里

                //如果存在就直接取值拿到指针
                pointer = (Long) service.hget("pointers", "point");
                logger.info("从redis中获取的缓存指针是" + pointer);
                pointer_str = (String) service.hget("pointers", "pointer_str");
                logger.info("从redis中获取到的pointer_str是" + pointer_str);
                readLog(logFile);
            }
        }, 2, 300, TimeUnit.SECONDS);

        exec.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                long start = new Date().getTime();//程序开始时间
                logger.info("入库开始时间是" + start + "毫秒");
                saveLog();
                long end = new Date().getTime();
                logger.info("入库结束时间是" + end + "毫秒");
                long time = end - start;
                logger.info("入库总耗时" + time + "毫秒");
            }
        }, 180, 600, TimeUnit.SECONDS);


    }

    //日志读取入缓存方法
    public void readLog(File logFile) {
        try {
            logger.info("正在读的文件是:" + logFile.getName());
            //指定文件可读可写
            BufferedRandomAccessFile randomFile = new BufferedRandomAccessFile(logFile, "rw");
            long len = logFile.length();
            logger.info("文件长度是" + len);

            //第一种情况
            if (len < pointer) {//日志清空并且长度小于上次记录的长度
                logger.info("日志文件被清空。从文件开始重新读取日志记录。");
                pointer = 0;
            }

            //第二种结果
            if (len == pointer) {//日志清空并且长度等于上次记录的长度
                logger.info("文件长度是" + len);
                logger.info("文件大小未改变,等待下一次扫描读取");
                long strLength = pointer_str.length();//获取最后存的那条数据的长度
                oldPointer = pointer - strLength - 1L;//指针回滚到这条数据之前(服务器上的规律)
                randomFile.seek(oldPointer);//移动文件指针位置
                String str = randomFile.readLine();
                if (!str.equals(pointer_str) && pointer_str != "" && pointer_str != null) {
                    pointer = 0;
                    logger.info("日志更新了,并且长度一样,指针归零");
                } else {
                    logger.info("日志文件没有增加,等待下次扫描");
                }
            }

            //第三种正常情况

            //获取RandomAccessFile对象文件指针的位置，初始位置是0
            logger.info("RandomAccessFile文件每次读取指针开始位置:" + pointer);
            oldPointer = 0;//对指针初始化
            if (pointer != 0) {//如果不是第一次执行程序,那么自然会有指针
                long strLength = pointer_str.length();//获取最后存的那条数据的长度
                oldPointer = pointer - strLength-1L;//指针回滚到这条数据之前
                logger.info("回滚到的上次指针位置是" + oldPointer);
            }

            randomFile.seek(oldPointer);//移动文件指针位置

            //判断每次开始读取时数据是否等于前一次最后读取的数据
            String threeStr = randomFile.readLine();//取到文件每次开始读的第一条数据

            logger.info("线程第二次启动读到的上次最后一条数据是:" + threeStr);
            logger.info("缓存里上次最后的一条是:" + pointer_str);

            //如果重读的数据等于缓存记录的最后一条数据,那么日志没有被更新
            if (!threeStr.equals(pointer_str) && pointer_str != "" && pointer_str != null) {
                pointer = 0;
                randomFile.seek(pointer);
                logger.info("日志更新了,文件重新读取");
            } else {
                logger.info("新的扫描读取开始,日志文件没有被更新,从上次记录位置读取");
                randomFile.seek(pointer);
            }

            long start = new Date().getTime();//程序开始时间
            logger.info("日志读取开始时间是" + start + "毫秒");
            logger.info("日志文件正在被读取,暂未找到有效数据......");

            String tmp = "";//定义文件读取的每一行数据
            int count = 0;
            Shares shares = new Shares();

            while ((tmp = randomFile.readLine()) != null) {
                //logger.info("读取的每一条数据是:" + tmp);
                copyTemp = tmp;//当文件读取完毕,将最后一条数据赋值给它,并存到缓存中
                if (tmp.contains("Got share")) {//如果找到关键数据就进行读取
                    count++;
                    logger.info("有效数据字符串:" + tmp + "共有" + count + "个");
                    stringData(tmp, shares, count, service);//对关键数据进行字符串切割
                }

                pointer = randomFile.getFilePointer();//文件指针每次移动的位置
                //logger.info("指针终止的位置:" + pointer);
                service.hput("pointers", "point", pointer);//将每次读取的最终指针加入到缓存中
                service.hput("pointers", "pointer_str", copyTemp);//将每次读取的最后一条数据存入到缓存里

                //logger.info("指针移动到:" + pointer);
                //int tmplength = tmp.length();
                //logger.info("字符串的长度是" + tmplength);
                //Thread.sleep(2000);
            }

            randomFile.close();//读取文件关闭

            long end = new Date().getTime();
            logger.info("日志读取结束时间是" + end + "毫秒");
            long time = end - start;
            logger.info("日志读取总耗时" + time + "毫秒");
        } catch (Exception e) {

            //实时读取日志异常，需要记录时间和lastTimeFileSize 以便后期手动补充
            System.out.println(sdf.format(new Date()) + "文件读取错误,最后指针的位置:" + pointer);

        } finally {
            //将lastTimeFileSize 落地以便下次启动的时候，直接从指定位置获取
        }
    }

    //从缓存中将数据入库
    public void saveLog() {
        SharesDaoImpl sharesDao = new SharesDaoImpl();
        //数据从缓存中入库
        int saveCount=0;
        Set<String> keys = service.getKeys();//获取键集合
        if (keys.isEmpty()) {
            logger.info("缓存中没有有效的数据");
        } else {
            for (String key : keys) {
                saveCount++;
                try {
                    Shares value = (Shares) service.get(key);
                    sharesDao.insert(value);//将值入库
                    logger.info("数据成功入库!!!总计"+saveCount);
                    service.remove(key);
                    logger.info("缓存已清除!!!");
                } catch (Exception e) {
                    logger.info("数据入库异常!");
                    e.printStackTrace();
                } finally {
                }
            }
        }
    }

    //对关键数据进行切割
    public void stringData(String tmp, Shares shares, int count, IRedisService service) {
        //logger.info("线程是" + Thread.currentThread().getName());
        //String height = tmp.substring(tmp.indexOf("height") + 6, tmp.indexOf("with")).trim();
        String nonce = tmp.substring(tmp.indexOf("nonce") + 5, tmp.indexOf("with difficulty")).trim();
        String difficultyStr = tmp.substring(tmp.indexOf("difficulty") + 11, tmp.indexOf("from")).trim();
        Float difficulty = Float.parseFloat(difficultyStr);
        //根据work关键字截取新的字符串
        String str = tmp.substring(tmp.indexOf("worker") + 6).trim();
        String workerid = str.substring(0, str.indexOf("-")).trim();
        //int workerId = Integer.parseInt(workerid);
        //String message_id = str.substring(str.indexOf("-") + 1).trim();
        shares.setRemHost("127.0.0.1");
        shares.setUsername(workerid);
        shares.setOurResult("Y");
        shares.setUpstreamResult("N");
        shares.setReason("");
        shares.setSolution("");
        shares.setDifficulty(difficulty);
        //shares.setHeight(height);
        //shares.setNonce(nonce);
        //shares.setTime(time);
        //shares.setWorkerId(workerId);

        //接下来对time字段进行格式转换
        //StringBuffer buf=new StringBuffer();//用于time时间拼接
        try {
            String time = tmp.substring(0, tmp.indexOf("WARN")).trim();//截取关键数据中的时间
            String year = getCurrentYear();//获取当前年份
            buf.append(year).append(" ").append(time);
            String times = buf.toString();
            Date myDate = sdf.parse(times);
            shares.setTime(myDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }


        try {
            service.put(nonce, shares, 1 * 3600);//设置1小时候过期
            System.out.println("存入缓存成功!");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("数据加入缓存异常!");
        }
    }

    //遍历文件夹寻找日志文件
    public void readLogDir(File fileDir) throws Exception {
        String fileName = "";

        if (fileDir != null) {
            File[] files = fileDir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".log")) {
                        fileName = file.getName();//文件名
                        String fileUrl = fileDir + "/" + fileName;//文件路径拼接
                        //logger.info("读取的日志文件是"+fileUrl);
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

    //获取年份
    public static String getCurrentYear() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return sdf.format(date);
    }

    public void stop() {
        if (exec != null) {
            exec.shutdown();
            logger.info("file read stop ！");
        }
    }
}