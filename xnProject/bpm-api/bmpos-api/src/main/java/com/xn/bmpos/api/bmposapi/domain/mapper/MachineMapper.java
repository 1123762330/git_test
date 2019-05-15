package com.xn.bmpos.api.bmposapi.domain.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xn.bmpos.api.bmposapi.domain.dao.Machine;
import com.xn.bmpos.api.bmposapi.domain.model.MachineWebModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * FileName:       MachineMapper
 * Author:         Administrator
 * Date:           2019/4/29 15:23
 * Description:
 */
@Mapper
public interface MachineMapper extends BaseMapper<Machine> {

    @Select("SELECT tc.coin_name AS coinTypeName, tm.mine_type AS mineType,tm.hashrate AS hashrate,tm.power AS POWER, tm.electricity AS electricity FROM  t_merge tm ,t_cointype tc WHERE tm.coin_id = tc.id")
    List<MachineWebModel> queryMachineAll();

    @Select("SELECT tc.coin_name AS coinTypeName, tm.mine_type AS mineType,tm.hashrate AS hashrate,tm.power AS POWER, tm.electricity AS electricity FROM  t_merge tm ,t_cointype tc WHERE tm.coin_id = tc.id And tm.coin_id = #{coinId}")
    List<MachineWebModel> queryMachineAllByCoin(@Param(value="coinId")int coinId);

}
