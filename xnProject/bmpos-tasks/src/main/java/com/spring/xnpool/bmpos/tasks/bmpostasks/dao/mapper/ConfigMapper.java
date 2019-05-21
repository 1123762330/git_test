package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper;


import com.spring.xnpool.bmpos.tasks.bmpostasks.SettingAPI.Config;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface ConfigMapper {
    /**
     * ===========================
     * @param name
     * @return
     */
    String findValueByKey(String name);




    Integer update(@Param("name") String name, @Param("value") String value, @Param("time") Date time);
/*===============================*/



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
    Integer updateByKey(Config config);

    /**
     * 找出所有的配置列表
     * @return
     */
    List<Config> findAll();
    /**
     * 添加配置参数
     */
    Integer insert(Config config);
    /**
     * 删除配置参数
     */
    Integer delete(Integer id);

    Config findByid(Integer id);

}
