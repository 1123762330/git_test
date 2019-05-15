package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Settings;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.SettingsModel;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface SettingsMapper extends Mapper<Settings> {
    @Select("SELECT  * FROM settings WHERE config_key = #{blockavg}")
    Settings selectByKey(String blockavg);

    @Select("SELECT NAME AS NAME ,VALUE  AS VALUE FROM settings WHERE NAME = #{pps_reward_type}")
    SettingsModel selectByName(String pps_reward_type);

    @Insert(" INSERT INTO  settings" +
            "            (name," +
            "             value)" +
            "VALUES (#{name}," +
            "        #{value});")
    int insertSettings(SettingsModel settings);


    @Update("UPDATE  settings" +
            " SET  value =#{value} WHERE name =#{name} ")
    void updateSettingValue(SettingsModel settings);




}