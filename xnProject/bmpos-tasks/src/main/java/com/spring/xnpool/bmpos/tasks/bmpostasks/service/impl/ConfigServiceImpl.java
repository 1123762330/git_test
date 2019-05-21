package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.SettingAPI.Config;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ConfigMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.*;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ConfigServiceImpl implements IConfigService {
    @Autowired
    private ConfigMapper configMapper;
    @Override
    public Config findByKey(String name) {
        return getByKey(name);
    }

    @Override
    public void updateByKey(Config config) {
        if(config.getId()==null){
            String name = config.getName();
            Config byKey = findByKey(name);
            if(byKey!=null){
                throw new DataExistException(name+"配置名已存在!");
            }
            insert(config);
        }else {
            Config data = findById(config.getId());
            if(data==null){
                throw new DataNotFoundException("该配置未找到!");
            }
            if(config.getName()==null||"".equals(config.getName())){
                config.setName(data.getName());
            }
            if(config.getRemark()==null||"".equals(config.getRemark())){
                config.setRemark(data.getRemark());
            }
            if(config.getValue()==null||"".equals(config.getValue())){
                config.setValue(data.getValue());
            }
            config.setTime(new Date());
            updatekey(config);
        }
    }

    @Override
    public List<Config> findAll() {
        return getAll();
    }

    @Override
    public void insert(Config config) {
        config.setTime(new Date());
        String name = config.getName();
        Config byKey = findByKey(name);
        if(byKey!=null){
            throw new DataExistException(name+"配置名已存在!");
        }
        insertConfig(config);
    }

    @Override
    public void delete(Integer id) {
        deleteConfing(id);

    }

    @Override
    public Config findByid(Integer id) {
        return findById(id);
    }

    @Override
    public List<Config> getDefaultConfig() {
        List<Config> setting = new ArrayList<>();
        setting.add(new Config("pps_last_share_id","1","预支付计算的最后记录位置"));
        setting.add(new Config("disable_auto_payouts","0","是否自动支付 0 表示开启 1 表示未开启"));
        setting.add(new Config("txlimit_auto","500","日结：获取分组值 （500）"));
        setting.add(new Config("donation_default","5","捐赠的数值"));
        setting.add(new Config("txfee_auto","0.1","TX费用,费用适用于交易"));
        setting.add(new Config("txfee_manual","0.1","TX费用,费用适用于交易"));
        setting.add(new Config("start_share_id","1","stares表统计开始id"));
        setting.add(new Config("start_share_time","2019-05-20 00:00:00","stares表统计开始id"));
        return setting;
    }

    private Config getByKey(String name){
        return configMapper.findByKey(name);
    }
    private void updatekey(Config config){
        Integer cows = configMapper.updateByKey(config);
        if(cows != 1){
            throw new UpdateException("修改数据时出现未知错误!");
        }
    }
    private List<Config> getAll(){
        return configMapper.findAll();
    }
    private void insertConfig(Config config){
        Integer rows = configMapper.insert(config);
        if(rows != 1){
            throw new InsertException("添加数据时出现未知错误!");
        }
    }
    private void deleteConfing(Integer id){
        Integer row = configMapper.delete(id);
        if(row != 1){
            throw new DeleteException("删除数据时出现未知错误!");
        }
    }
    private Config findById(Integer id){
        return configMapper.findByid(id);
    }
}
