package com.xnpool.account.mappers;


import com.xnpool.account.entity.Role;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Mapper
@Repository
public interface RoleMapper {

    @Insert("INSERT INTO t_role(rolename, roleremark,createtime, updatetime)" +
            "VALUES (#{rolename}, #{roleremark}, #{createtime}, #{updatetime})")
    @Options(useGeneratedKeys = true, keyProperty = "roleid", keyColumn = "id")
    void insertRole(Role role);

    @Select("select * from t_role t where t.roleid = #{roleid}")
    Role SelectRoleById(@RequestParam(name = "roleid") int roleid);

    @Delete("DELETE FROM  t_role  where roleid =#{roleid}")
    int deleteRoleById(@RequestParam(name = "roleid") int roleid);

    @Update("UPDATE t_role  t SET  t.rolename  = #{rolename} , t.updatetime =#{updatetime}, t.roleremark =#{roleremark} WHERE t.roleid =#{roleid} ")
    int updateRole(@Param("rolename") String rolename, @Param("updatetime") Date updatetime, @Param("roleremark") String roleremark, @Param("roleid") int roleid);
}
