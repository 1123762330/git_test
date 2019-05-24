package com.xn.bpmworkerevents.controller;

import com.xn.bpmworkerevents.config.SpringUtils;
import com.xn.bpmworkerevents.dao.PoolWorkerMapper;
import com.xn.bpmworkerevents.dao.ShareModelMapper;
import com.xn.bpmworkerevents.entity.PoolWorker;
import com.xn.bpmworkerevents.entity.Shares;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * FileName:       InShares
 * Author:         Administrator
 * Date:           2019/5/23 16:08
 * Description:
 */
@Component
public class Work {

    @Autowired
    private PoolWorkerMapper poolWorkerMapper;

    @Autowired
    private ShareModelMapper shareModelMapper;

    public void insertPoolWorker(List<PoolWorker> list){
        this.poolWorkerMapper = SpringUtils.getBean(PoolWorkerMapper.class);
        poolWorkerMapper.insertPoolWorker(list);
    }

    public void insertShares(List<Shares> list){
        this.shareModelMapper = SpringUtils.getBean(ShareModelMapper.class);
        shareModelMapper.insertShares(list);
    }

}
