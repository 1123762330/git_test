package com.xn.console.bmposconsole.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xn.console.bmposconsole.dao.model.CoinType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CointypeMapper extends BaseMapper<CoinType> {

    @Select("select coin_name As coinName,url from t_cointype WHERE coin_name = #{coin}")
    CoinType getPluginsByCoin(String coin);
    @Select("select coin_name from t_cointype where url is not null And url != \"\" group by coin_name")
    List<String> getCoin();
}
