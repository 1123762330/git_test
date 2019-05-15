package com.xnpool.account.mappers;

import com.xnpool.account.entity.LoginHistroy;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface LoginHistroyMapper {

    @Insert("INSERT INTO t_loginhistroy(user_id,login_ip,login_place,login_facility,login_time) " +
            "VALUES(#{userId},#{loginIp},#{loginPlace},#{loginFacility},#{loginTime})" )
    int insertLoginHistroy(@Param("userId") int userId, @Param("loginIp") String loginIp, @Param("loginPlace") String loginPlace,
                           @Param("loginFacility") String loginFacility, @Param("loginTime") String loginTime);

    @Select("SELECT login_time AS loginTime,login_ip AS loginIp,login_place AS loginPlace,login_facility AS loginFacility FROM t_loginhistroy " +
            "WHERE user_id = #{userId} ORDER BY id DESC LIMIT 6")
    List<LoginHistroy> getLoginHsByUid(@Param("userId") int userId);

}
