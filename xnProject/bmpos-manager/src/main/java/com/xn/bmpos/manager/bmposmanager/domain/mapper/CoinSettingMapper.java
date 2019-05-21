package com.xn.bmpos.manager.bmposmanager.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinSetting;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CoinSettingMapper extends BaseMapper<CoinSetting> {

    @Select("select * from t_coin_setting where isAccept !='N' ")
    List<CoinSetting> selectAllCoin();

    @Select("SELECT * FROM t_coin_setting where name=#{coin}")
    CoinSetting selectCoin (String coin);

    @Select("select coin_name  from t_cointype")
    List<String> queryAllCoinType ();
}
