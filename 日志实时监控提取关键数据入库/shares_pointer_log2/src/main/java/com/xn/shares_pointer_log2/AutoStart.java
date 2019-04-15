package com.xn.shares_pointer_log2;


import com.xn.shares_pointer_log2.controller.LogView;
import com.xn.shares_pointer_log2.controller.LogView2;
import com.xn.shares_pointer_log2.domain.LogParameterModel;
import com.xn.shares_pointer_log2.service.IRedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
@Order(1)
@EnableScheduling
public class AutoStart extends LogParameterModel implements CommandLineRunner {
    @Value("${log_path_prefix}")
    private String path;

    @Value("${log_path_prefix2}")
    private String path2;

    @Autowired
    private LogView2 logView2;

    @Autowired
    private LogView logView;


    //程序启动入口
    @Override
    public void run(String... args) throws Exception {
        init();//初始化指针和从缓存中取值
        File file1 = logView.readLogDir(new File(path));
        File file2 = new File(path2 + "/" + "grin-server.log");

        logView.realtimeShowLog(file1);//开启第一个日志的多线程
        logView2.realtimeShowLog2(file2);//开启读取第二个日志的线程
        logView.savetimeLog();//开启入库存储的线程
    }
}
