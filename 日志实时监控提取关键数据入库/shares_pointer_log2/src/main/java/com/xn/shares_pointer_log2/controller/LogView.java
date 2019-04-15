package com.xn.shares_pointer_log2.controller;

import com.xn.shares_pointer_log2.dao.impl.SharesDaoImpl;
import com.xn.shares_pointer_log2.domain.Log2Shares;
import com.xn.shares_pointer_log2.domain.LogParameterModel;
import com.xn.shares_pointer_log2.domain.Shares;
import com.xn.shares_pointer_log2.service.IRedisService;
import com.xn.shares_pointer_log2.utils.BufferedRandomAccessFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@EnableScheduling
public class LogView extends LogParameterModel {

    @Autowired
    private IRedisService service;

    //读取第一个日志读取的线程
    public void realtimeShowLog(File file1) throws IOException, InterruptedException {
        if (file1 == null) {
            throw new IllegalStateException("文件1没有被找到");
        }

        exec.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                //service.hput("pointers", "point1", 143628254L);//将每次读取的最终指针加入到缓存中
                //service.hput("pointers", "pointer_str1", "Apr 06 14:28:35.099 TRCE MWGrinPool - Got Message from upstream Server: \"{\\\"id\\\":\\\"Stratum\\\",\\\"jsonrpc\\\":\\\"2.0\\\",\\\"method\\\":\\\"job\\\",\\\"params\\\":{\\\"difficulty\\\":13874,\\\"height\\\":107219,\\\"job_id\\\":9,\\\"pre_pow\\\":\\\"0001000000000001a2d3000000005ca84713028bc36a38354d2dbba589775627a46d15c8c0963b9c71d51b89f47922e09e7d15c73edd8b736899c15b95c161076ac6e2e7922c41b9746782674b1eb31d3983abb9851a2f6da418ef49a703ece266f415e16bfaadb289ce3d56dd48e216aa38b66b8e376bd4ac613efd95ec4600292f49cfbdb62b43fdfc0bb6aa9ece6b8df9196717e197886b27b0a50aa086b59172f6cb1641d6753b5c2b587eaec1e351144a3eebd16cc7c66e6c640327120e2ef07f345ab800a1a88e7e67e908635e660800000000000513cb000000000004087e00000003d3489d9c00000165\"}}\\n");//将每次读取的最后一条数据存入到缓存里

                //如果存在就直接取值拿到指针
                pointer1 = (Long) service.hget("pointers", "point1");
                logger.info("从redis中获取的日志1的缓存指针是" + pointer1);
                pointer_str1 = (String) service.hget("pointers", "pointer_str1");
                logger.info("从redis中获取到的日志1的pointer_str1是" + pointer_str1);
                BufferedRandomAccessFile randomFile1 = null;
                try {
                    randomFile1 = new BufferedRandomAccessFile(file1, "rw");
                    readLog(file1, randomFile1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }, 30, 300, TimeUnit.SECONDS);
    }

    //开启入库存储的线程
    public void savetimeLog() {
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
        }, 600, 600, TimeUnit.SECONDS);
    }

    //日志1读取入缓存方法
    public void readLog(File file1, BufferedRandomAccessFile randomFile1) {
        try {
            logger.info("正在读的文件1是:" + file1.getName());
            //指定文件可读可写

            long len = file1.length();
            logger.info("文件1长度是" + len);

            long pointerOne = threePatterns(len, randomFile1, pointer1, pointer_str1);//调用方法

            //获取RandomAccessFile对象文件指针的位置，初始位置是0
            logger.info("RandomAccessFile文件每次读取指针开始位置:" + pointerOne);

            if (pointerOne == len) {
                logger.info("文件1读取到末尾了");
                return;
            } else {
                randomFile1.seek(pointerOne);
            }

            long start = new Date().getTime();//程序开始时间
            logger.info("日志1读取开始时间是" + start + "毫秒");
            logger.info("日志1正在被读取,暂未找到有效数据......");

            String tmp = "";//定义文件读取的每一行数据
            int count = 0;
            Shares shares = new Shares();
            Thread.sleep(3000);
            while ((tmp = randomFile1.readLine()) != null) {
                //logger.info("读取的每一条数据是:" + tmp);
                copyTemp = tmp;//当文件读取完毕,将最后一条数据赋值给它,并存到缓存中
                if (tmp.contains("Got share")) {//如果找到关键数据就进行读取
                    count++;
                    logger.info("有效数据字符串:" + tmp + "共有" + count + "个");
                    stringData(tmp, shares, service);//对关键数据进行字符串切割
                }

                pointer1 = randomFile1.getFilePointer();//文件指针每次移动的位置

                service.hput("pointers", "point1", pointer1);//将每次读取的最终指针加入到缓存中
                service.hput("pointers", "pointer_str1", copyTemp);//将每次读取的最后一条数据存入到缓存里
                //logger.info("指针终止的位置:" + pointer1);
                //logger.info("存到缓存中的数据是:"+copyTemp);
                //int tmplength = tmp.length();
                //logger.info("字符串的长度是" + tmplength);
                //Thread.sleep(3000);
            }

            randomFile1.close();//读取文件关闭

            long end = new Date().getTime();
            logger.info("日志1读取结束时间是" + end + "毫秒");
            long time = end - start;
            logger.info("日志1读取总耗时" + time + "毫秒");
        } catch (Exception e) {
            logger.info("异常是"+e.getMessage());
            //实时读取日志异常，需要记录时间和lastTimeFileSize 以便后期手动补充
            System.out.println(sdf.format(new Date()) + "文件1读取错误,最后指针的位置:" + pointer1);

        } finally {
            //将lastTimeFileSize 落地以便下次启动的时候，直接从指定位置获取
        }
    }

    //将缓存中的日志数据入库
    public void saveLog() {
        SharesDaoImpl sharesDao = new SharesDaoImpl();
        //数据从缓存中入库
        int saveCount = 0;
        Set<String> keys = service.getKeys();//获取键集合
        if (keys.isEmpty()) {
            logger.info("缓存中没有有效的数据");
        } else {
            for (String key : keys) {

                try {
                    saveCount++;
                    Shares value = (Shares) service.get(key);
                    logger.info("获取到的value:"+value.toString());
                    sharesDao.insert(value);//将值入库
                    service.remove(key);//删除日志1的缓存
                    service.removeKey(REDIS_KEY2, key);//删除日志2的缓存
                } catch (Exception e) {
                    logger.info("数据入库异常!");
                    e.printStackTrace();
                } finally {
                }
            }
            logger.info("缓存已清除 !!!" + saveCount);
        }
    }

    //对日志1和诶之2关键数据进行切割合并
    public void stringData(String tmp, Shares shares, IRedisService service) {
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

        //shares.setHeight(height);
        //shares.setNonce(nonce);
        //shares.setTime(time);
        //shares.setWorkerId(workerId);

        //从日志2中调用关键数据
        Integer bits = 0;
        String ourResult = "";
        String reason = "";
        Set<String> Log2keys = service.getKeys(REDIS_KEY2);
        if (Log2keys.isEmpty()) {
            logger.info("缓存中没有有效的数据");
        }

        if (Log2keys.contains(nonce)) {
            try {
                Log2Shares log2Shares = (Log2Shares) service.hget(REDIS_KEY2, nonce);
                logger.info("找到的合并对象是" + log2Shares.toString());
                bits = log2Shares.getBits();
                ourResult = log2Shares.getOurResult();
                reason = log2Shares.getReason();
            } catch (Exception e) {
                logger.info("合并对象出现异常");
                e.printStackTrace();
            }
        } else {
            bits = 0;
            ourResult = "Y";
            reason = "";
        }

        //对数据进行封装
        shares.setRemHost("127.0.0.1");
        shares.setUsername(workerid);
        shares.setOurResult(ourResult);
        shares.setUpstreamResult("N");
        shares.setReason(reason);
        shares.setSolution("");
        shares.setDifficulty(difficulty);
        shares.setBits(bits);


        //接下来对time字段进行格式转换
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
            logger.info("加入缓存成功");
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("数据加入缓存异常!");
        }
    }

    //遍历文件夹寻找日志1文件
    public File readLogDir(File file1Dir) throws Exception {
        String fileName = "";

        if (file1Dir != null) {
            File[] files = file1Dir.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".log")) {
                        fileName = file.getName();//文件名
                        String fileUrl = file1Dir + "/" + fileName;//文件路径拼接
                        //logger.info("读取的日志文件是"+fileUrl);
                        File file1 = new File(fileUrl);
                        return file1;
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
        return null;
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