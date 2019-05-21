package com.xn.bmpos.manager.bmposmanager.domain.mapper;

import com.xn.bmpos.manager.bmposmanager.domain.dao.CoinType;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * FileName:       CoinTypeMapper
 * Author:         Administrator
 * Date:           2019/5/16 12:07
 * Description:
 */
@Repository
@Mapper
public interface CoinTypeMapper {

    @Select("SELECT id,coin_name AS coinName,price FROM t_cointype")
    List<CoinType> findAllCoin();

    @Update("UPDATE t_cointype SET  price = #{price} WHERE coin_name = #{coinName}")
    int updateCoinPrice(@Param(value = "coinName") String coinName, @Param(value = "price") String price);

}
