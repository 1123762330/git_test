package com.xn.threadconsumer.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Configs {

    /*@Value("${kafka.consumer.zookeeper.connect}")
    String zook;*/

    @Value("${spring.kafka.consumer.bootstrap-servers}")
    String brokerList;

    @Value("${kafka.consumer.group.id}")
    String groupId;

    @Value("#{'${kafka.consumer.topic}'.split(',')}")
    String topic;

    @Value("${kafka.consumer.enable.auto.commit}")
    String commit;

    @Value("${kafka.consumer.auto.commit.interval}")
    String interval;

    @Value("${kafka.consumer.session.timeout}")
    String timeout;

    @Value("${kafka.consumer.auto.offset.reset}")
    String reset;

    @Value("${spring.kafka.consumer.key-serializer}")
    String keyzer;

    @Value("${spring.kafka.consumer.value-deserializer}")
    String valuezer;

    /*public String getZook() {
        return zook;
    }

    public void setZook(String zook) {
        this.zook = zook;
    }*/

    public String getBrokerList() {
        return brokerList;
    }

    public void setBrokerList(String brokerList) {
        this.brokerList = brokerList;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getReset() {
        return reset;
    }

    public void setReset(String reset) {
        this.reset = reset;
    }

    public String getKeyzer() {
        return keyzer;
    }

    public void setKeyzer(String keyzer) {
        this.keyzer = keyzer;
    }

    public String getValuezer() {
        return valuezer;
    }

    public void setValuezer(String valuezer) {
        this.valuezer = valuezer;
    }

    @Override
    public String toString() {
        return "Configs{" +
                "brokerList='" + brokerList + '\'' +
                ", groupId='" + groupId + '\'' +
                ", topic='" + topic + '\'' +
                ", commit='" + commit + '\'' +
                ", interval='" + interval + '\'' +
                ", timeout='" + timeout + '\'' +
                ", reset='" + reset + '\'' +
                ", keyzer='" + keyzer + '\'' +
                ", valuezer='" + valuezer + '\'' +
                '}';
    }
}
