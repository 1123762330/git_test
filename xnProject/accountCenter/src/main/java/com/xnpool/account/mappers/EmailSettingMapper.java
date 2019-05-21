package com.xnpool.account.mappers;

import com.xnpool.account.entity.EmailSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * FileName:       EmailSettingMapper
 * Author:         Administrator
 * Date:           2019/5/17 10:30
 * Description:
 */
@Mapper
@Repository
public interface EmailSettingMapper {

    @Select("SELECT * FROM t_setting")
    EmailSetting findEmailSetting ();

}
