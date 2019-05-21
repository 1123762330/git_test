package com.xn.console.bmposconsole.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xn.console.bmposconsole.dao.model.CoinSetting;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CoinSettingMapper extends BaseMapper<CoinSetting> {

    @Select("select * from t_coin_setting")
    List<CoinSetting> selectAllCoin ();

    @Select("SELECT * FROM `t_coin_setting` where name=#{coin}")
    CoinSetting selectCoin (String coin);

    @Select("select coin_name  from t_cointype")
    List<String> queryAllCoinType ();

    @Insert("INSERT INTO t_coin_setting (NAME,fee,blockPrice,rpchost,node,value,image,unit,scale,isAccept)\n" +
            "VALUES\n" +
            "(#{name},#{fee},#{blockPrice},#{rpchost},#{node},#{value},#{image},#{unit}, #{scale},#{isAccept})")
    Integer addCoinToSetting (CoinSetting coinSetting);

    @Update("UPDATE t_coin_setting SET NAME=#{name},fee=#{fee},blockPrice=#{blockPrice}, rpchost=#{rpchost}" +
            ",node=#{node},value=#{value},image=#{image}, unit=#{unit},scale=#{scale},isAccept=#{isAccept}  WHERE id={#id}\n")
    Integer updateCoinToSetting (CoinSetting coinSetting);

    @Select("SELECT * FROM t_coin_setting WHERE id=#{id}")
    CoinSetting findCoinbyId (Integer id);
}
