package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;

import com.spring.xnpool.bmpos.tasks.bmpostasks.SettingAPI.Config;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ConfigMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.DataNotFoundException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.DeleteException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.InsertException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.exception.UpdateException;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
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
            insert(config);
        }
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

    @Override
    public List<Config> findAll() {
        return getAll();
    }

    @Override
    public void insert(Config config) {
        config.setTime(new Date());
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
