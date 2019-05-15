package com.xn.bmpos.manager.bmposmanager.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xn.bmpos.manager.bmposmanager.domain.dao.Setting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface SettingMapper extends BaseMapper<Setting> {

    @Select("select * from t_setting")
    List<Setting> selectAllCoin();

    @Select("SELECT * FROM `t_setting` where name=#{coin}")
    Setting selectCoin (String coin);

    @Select("select coin_name  from t_cointype")
    List<String> queryAllCoinType ();
}
