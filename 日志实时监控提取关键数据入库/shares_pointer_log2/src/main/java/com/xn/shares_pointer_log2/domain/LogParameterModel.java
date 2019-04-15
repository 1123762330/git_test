package com.xn.shares_pointer_log2.domain;


import com.xn.shares_pointer_log2.controller.LogView;
import com.xn.shares_pointer_log2.service.IRedisService;
import com.xn.shares_pointer_log2.utils.BufferedRandomAccessFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class LogParameterModel {
    @Autowired
    private IRedisService service;

    public long pointer1 = 0L;//文件1读取指针
    public long pointer2 = 0L;//文件2读取指针
    public String pointer_str1;//缓存中记录的每次读取的最后一条数据
    public String pointer_str2;//缓存中记录的每次读取的最后一条数据
    public String copyTemp;//文件1每次读取到最后一行,将整行数据赋值给它,并添加到缓存中
    public String copyTemp2;//文件2每次读取到最后一行,将整行数据赋值给它,并添加到缓存中
    public long oldPointer;//指针回滚到最后一条数据之前,方便程序读取最后一条数据
    public final String REDIS_KEY2 = "Log2Shares_key";//日志2的大键
    public SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd HH:mm:ss", java.util.Locale.ENGLISH);
    public final Logger logger = LoggerFactory.getLogger(LogParameterModel.class);
    public StringBuffer buf = new StringBuffer();
    public ScheduledExecutorService exec = Executors.newScheduledThreadPool(3);//启动一个线程每10秒钟读取新增的日志信息

    //初始判断
    public void init() {
        boolean hKeyExists = service.isHKeyExists("pointers", "point1");//首先文件1判断指针键有没有
        boolean h2KeyExists = service.isHKeyExists("pointers", "point2");//首先判断文件2指针键有没有

        if (!hKeyExists) {//如果文件1不存在,就赋值为0
            service.hput("pointers", "point1", 0L);
            logger.info("指针不存在,将文件1指针重置为0,并加入缓存!");
            pointer1 = 0;
        } else {//如果存在就直接取值拿到指针
            pointer1 = (Long) service.hget("pointers", "point1");
            logger.info("启动时从redis中获取的文件1缓存指针是" + pointer1);
            pointer_str1 = (String) service.hget("pointers", "pointer_str1");
            logger.info("启动时获取上次文件1最后读取的字符串是:" + pointer_str1);
        }

        if (!h2KeyExists) {//如果文件2不存在,就赋值为0
            service.hput("pointers", "point2", 0L);
            logger.info("指针不存在,将文件2指针重置为0,并加入缓存!");
            pointer2 = 0;
        } else {//如果存在就直接取值拿到指针
            pointer2 = (Long) service.hget("pointers", "point2");
            logger.info("启动时从redis中获取文件2的缓存指针是" + pointer2);
            pointer_str2 = (String) service.hget("pointers", "pointer_str2");
            logger.info("启动时获取上次文件2最后读取的字符串是:" + pointer_str2);
        }
    }

    //三种情况判断
    public long threePatterns(long length, BufferedRandomAccessFile randomFile, long pointer, String pointer_str) {
        //第一种情况
        long poin = 0L;
        if (length > pointer) {//第三种情况(最常用)
            try {
                oldPointer = 0;//对指针初始化
                if (pointer != 0) {//如果不是第一次执行程序,那么自然会有指针
                    long strLength = pointer_str.length();//获取最后存的那条数据的长度
                    oldPointer = pointer - strLength-1L;//指针回滚到这条数据之前(服务器上的读取规律)
                    //oldPointer = pointer - strLength;//本机服务器上的规律
                    logger.info("回滚到的上次指针位置是" + oldPointer);
                }
                randomFile.seek(oldPointer);//移动文件指针位置
                //判断每次开始读取时数据是否等于前一次最后读取的数据
                String threeStr = randomFile.readLine();//取到文件每次开始读的第一条数据

                logger.info("线程第二次启动读到的上次最后一条数据是:" + threeStr);
                logger.info("大于的时候缓存里上次最后的一条是:" + pointer_str);

                //如果重读的数据等于缓存记录的最后一条数据,那么日志没有被更新
                if (!threeStr.equals(pointer_str) && pointer_str != "" && pointer_str != null) {
                    logger.info("日志更新了,文件重新读取!");
                    pointer = 0;
                }
                logger.info("扫描读取开始,从上次记录位置读取!");
                poin = pointer;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (length < pointer) {//日志清空并且长度小于上次记录的长度
            logger.info("日志文件被清空。从文件开始重新读取日志记录。");
            poin = 0;
        }

        if (length == pointer) {//日志清空并且长度等于上次记录的长度(第二种情况)
            try {
                logger.info("文件大小没改变,等待下一次扫描读取");
                long strLength = pointer_str.length();//获取最后存的那条数据的长度
                oldPointer = pointer - strLength - 1L;//指针回滚到这条数据之前(服务器上的)
                //oldPointer = pointer - strLength;//本机上的

                randomFile.seek(oldPointer);//移动文件指针位置
                String threeStr = randomFile.readLine();

                logger.info("线程第二次启动读到的上次最后一条数据是:" + threeStr);
                logger.info("等于的时候缓存里上次最后的一条是:" + pointer_str);

                if (!threeStr.equals(pointer_str) && pointer_str != "" && pointer_str != null) {
                    logger.info("日志更新了,并且长度一样,指针归零");
                    pointer = 0;
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            poin = pointer;
        }
        return poin;
    }
}
