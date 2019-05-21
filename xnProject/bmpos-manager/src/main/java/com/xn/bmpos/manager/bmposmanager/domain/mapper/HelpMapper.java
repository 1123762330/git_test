package com.xn.bmpos.manager.bmposmanager.domain.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xn.bmpos.manager.bmposmanager.domain.dao.Help;
import com.xn.bmpos.manager.bmposmanager.domain.dao.HelpText;
import com.xn.bmpos.manager.bmposmanager.domain.model.HelpModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface HelpMapper extends BaseMapper<Help> {

    @Select("select * from  t_help")
    List<Help> selectAll();

    @Select("select * from  t_help_text where help_id = #{helpId}")
    HelpText SelectTestByPid(@Param("helpId") int helpId);
}
