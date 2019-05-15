package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import com.spring.xnpool.bmpos.tasks.bmpostasks.SettingAPI.Config;

import java.util.List;

public interface IConfigService {
    /**
     * 通过key查找value和name
     * @param name
     * @return
     */
    Config findByKey(String name);

    /**
     * 通过key修改value和name
     * @param config
     * @return
     */
    void updateByKey(Config config);

    /**
     * 找出所有的配置列表
     * @return
     */
    List<Config> findAll();
    /**
     * 添加配置参数
     */
    void insert(Config config);
    /**
     * 删除配置参数
     */
    void delete(Integer id);

    Config findByid(Integer id);
}
