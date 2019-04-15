package com.xn.shares_pointer_log2.controller;

import com.xn.shares_pointer_log2.domain.Log2Shares;
import com.xn.shares_pointer_log2.domain.LogParameterModel;
import com.xn.shares_pointer_log2.service.IRedisService;
import com.xn.shares_pointer_log2.utils.BufferedRandomAccessFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
public class LogView2 extends LogParameterModel {

    @Autowired
    private IRedisService service;

    //开启读取第二个日志的线程
    public void realtimeShowLog2(File file2) throws IOException {
        if (file2 == null) {
            throw new IllegalStateException("文件2没有被找到");
        }

        //读取第二个日志的线程
        exec.scheduleWithFixedDelay(new Runnable() {
            public void run() {
                //strCount = 0;
                //service.hput("pointers", "point", 249112277L);//将每次读取的最终指针加入到缓存中
                //service.hput("pointers", "pointer_str", "Apr 11 12:01:29.110 DEBG Grin Pool - broadcasting a job to 1 workers");//将每次读取的最后一条数据存入到缓存里

                //如果存在就直接取值拿到指针
                pointer2 = (Long) service.hget("pointers", "point2");
                logger.info("从redis中获取的文件2缓存指针是" + pointer2);
                pointer_str2 = (String) service.hget("pointers", "pointer_str2");
                logger.info("从redis中获取文件2的pointer_str2是" + pointer_str2);
                try {
                    //开启读第二个日志程序
                    //文件,指针对象,文件2的指针,文件2的每次最后保存的那条数据
                    BufferedRandomAccessFile randomFile2 = new BufferedRandomAccessFile(file2, "rw");
                    readLog2(file2, randomFile2);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, 2, 120, TimeUnit.SECONDS);

    }

    //第二个日志读取存入缓存方法
    public void readLog2(File logFile, BufferedRandomAccessFile randomFile) throws Exception {
        //如果存在就直接取值拿到指针
        /*pointer2 = (Long) service.hget("pointers", "point2");
        logger.info("从redis中获取的文件2缓存指针是" + pointer2);
        pointer_str2 = (String) service.hget("pointers", "pointer_str2");
        logger.info("从redis中获取文件2的pointer_str2是" + pointer_str2);*/

        try {
            logger.info("文件2是:" + logFile.getName());
            //指定文件可读可写
            long len2 = logFile.length();
            logger.info("文件2长度是" + len2);

            //调用可能出现的情况,第三种情况(pointer2直接从缓存里面取到)
            long poin = threePatterns(len2, randomFile,  pointer2, pointer_str2);

            //获取RandomAccessFile对象文件指针的位置，初始位置是0
            logger.info("RandomAccessFile文件每次读取指针开始位置:" + poin);

            if (poin == len2) {
                logger.info("文件2读取到末尾了");
                return;
            } else {
                randomFile.seek(poin);
            }

            String tmp2 = "";//定义文件读取的每一行数据
            int count2 = 0;
            Log2Shares log2Shares = new Log2Shares();

            long start = new Date().getTime();//程序开始时间
            logger.info("日志2读取开始时间是" + start + "毫秒");
            logger.info("日志2正在被读取,暂未找到有效数据......");

            while ((tmp2 = randomFile.readLine()) != null) {
                //logger.info("读取的每一条数据是:" + tmp2);
                copyTemp2 = tmp2;//当文件读取完毕,将最后一条数据赋值给它,并存到缓存中

                if (tmp2.contains("at height")) {//如果找到关键数据就进行读取
                    try {
                        count2++;
                        logger.info("日志2读取的有效数据:" + tmp2 + "共有" + count2 + "个");
                        Log2Shares shares2 = log2StrSpilt(tmp2, log2Shares);//对关键数据进行字符串切割
                        service.hashPut(shares2.getNonce(), shares2, 1 * 3600 * 24);
                    } catch (Exception e) {
                        logger.info("日志2的数据添加缓存失败");
                    }
                }
                //Thread.sleep(3000);
                pointer2 = randomFile.getFilePointer();//文件指针每次移动的位置

                service.hput("pointers", "point2", pointer2);//将每次读取的最终指针加入到缓存中
                service.hput("pointers", "pointer_str2", copyTemp2);//将每次读取的最后一条数据存入到缓存里
            }

            logger.info("日志2的关键数据成功加入缓存!共" + count2);
            randomFile.close();//读取文件关闭

            long end = new Date().getTime();
            logger.info("日志2读取结束时间是" + end + "毫秒");
            long time = end - start;
            logger.info("日志2读取总耗时" + time + "毫秒");
        } catch (Exception e) {

            //实时读取日志异常，需要记录时间和lastTimeFileSize 以便后期手动补充
            System.out.println(sdf.format(new Date()) + "文件2读取错误,最后指针的位置:" + pointer2);

        } finally {
            //将lastTimeFileSize 落地以便下次启动的时候，直接从指定位置获取
        }
    }

    //第二个日志对关键数据进行切割
    public Log2Shares log2StrSpilt(String tmp, Log2Shares shares2) {
        String ourResult;
        String reason = "";
        String edge_bits = tmp.substring(tmp.indexOf("bits") + 4, tmp.indexOf(", nonce")).trim();
        int bits = Integer.parseInt(edge_bits);
        //logger.info("edge_bits是" + edge_bits + "在第" + count + "行");
        String nonce = tmp.substring(tmp.indexOf("nonce") + 5, tmp.indexOf(", job_id")).trim();
        //logger.info("产生的nonce是" + nonce + "在第" + count + "行");
        if (tmp.contains("ERROR")) {
            ourResult = "N";
            //logger.info("错误数据是" + tmp);
            reason = tmp.substring(tmp.indexOf("submitted"));
            shares2.setReason(reason);
            shares2.setOurResult(ourResult);
        } else {
            ourResult = "Y";
            shares2.setReason(reason);
            shares2.setOurResult(ourResult);
        }

        shares2.setBits(bits);
        shares2.setNonce(nonce);
        //logger.info("有效的shares2数据:" + shares2.toString() + count);
        return shares2;

    }
}