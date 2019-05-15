package com.spring.xnpool.bmpos.tasks.bmpostasks.config;


import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.log.LoggerMessage;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.LoggerQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import javax.annotation.PostConstruct;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Configuration
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    private Map<String,LoggerMessage> loggerMessageMap = new ConcurrentHashMap<>();

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket")
                .setAllowedOrigins("*")
                .withSockJS();
    }
    /**
     * 推送日志到/topic/pullLogger
     */
    @PostConstruct
    public void pushLogger(){
        ExecutorService executorService=Executors.newFixedThreadPool(2);
        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        LoggerMessage log = LoggerQueue.getInstance().poll();
                        if(log!=null){
                            if(messagingTemplate!=null)
                                messagingTemplate.convertAndSend("/topic/pullLogger",log);
                                //System.err.println("getTimestamp:"+log.getTimestamp()+"   "+log);
                            loggerMessageMap.put(log.getTimestamp(),log);
                            //System.err.println("loggerMessageMap:=="+loggerMessageMap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        executorService.submit(runnable);
    }

    public LoggerMessage getLogByTime(String time){
     return loggerMessageMap.get(time);
    }

    public Map<String, LoggerMessage> getLoggerMessageMap() {
        return loggerMessageMap;
    }
}

