package com.xnpool.account.mappers;

import com.xnpool.account.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMaper {
    @Select("select * from t_user t where t.name =#{name} ")
    User getUserByName(String name);

    @Select("select * from t_user t where t.mobile =#{mobile} ")
    User getUserByMobile(String mobile);

    @Select("select * from t_user t where t.email =#{email} ")
    User getUserByEmail(String email);

    @Update("UPDATE t_user  t SET  t.name  = #{userName} , t.email =#{userEmail}, t.mobile =#{userPhone},t.IP = #{loginIp},t.state = #{state},t.is_app =#{is_app}" +
            " WHERE userid =#{userid} ")
    int updateUser(@Param(value = "userName") String userName, @Param(value = "userEmail") String userEmail,
                   @Param(value = "userPhone") String userPhone, @Param(value = "loginIp") String loginIp,
                   @Param(value = "state") int state, @Param(value = "is_app") String is_app,
                   @Param(value = "userid") int userid);


    @Insert("INSERT INTO t_user\n" +
            "            (roleid,\n" +
            "             mobile,\n" +
            "             pwd,\n" +
            "             code,\n" +
            "             codesenttime,\n" +
            "             enableflag,\n" +
            "             nick,\n" +
            "             qq,\n" +
            "             head,\n" +
            "             name,\n" +
            "             sex,\n" +
            "             email,\n" +
            "             company,\n" +
            "             job,\n" +
            "             intro,\n" +
            "             qrcode,\n" +
            "             street,\n" +
            "             createtime,\n" +
            "             logintime,\n" +
            "             wxopenid,\n" +
            "             wxhead,\n" +
            "             wxnick,\n" +
            "             dealerflag,\n" +
            "             trynum,\n" +
            "             IP,\n" +
            "             state," +
            "             is_app)\n" +
            "VALUES (#{roleid},\n" +
            "        #{mobile},\n" +
            "        #{pwd},\n" +
            "        #{code},\n" +
            "        #{codesenttime},\n" +
            "        #{enableflag},\n" +
            "        #{nick},\n" +
            "        #{qq},\n" +
            "        #{head},\n" +
            "        #{name},\n" +
            "        #{sex},\n" +
            "        #{email},\n" +
            "        #{company},\n" +
            "        #{job},\n" +
            "        #{intro},\n" +
            "        #{qrcode},\n" +
            "        #{street},\n" +
            "        #{createtime},\n" +
            "        #{logintime},\n" +
            "        #{wxopenid},\n" +
            "        #{wxhead},\n" +
            "        #{wxnick},\n" +
            "        #{dealerflag},\n" +
            "        #{trynum},\n" +
            "        #{IP},\n" +
            "        #{state},\n" +
            "        #{is_app});")
    @Options(useGeneratedKeys = true, keyProperty = "userid", keyColumn = "id")
    void insertUser(User user);

    @Update("update t_user t SET  t.email =#{email}  WHERE t.name =#{username}")
    int updateUserEamilByName(@Param(value = "username") String username, @Param(value = "email") String email);

    @Update("update t_user t SET  t.mobile =#{phone} WHERE t.name =#{username}")
    void updateUserPhoneByName(@Param(value = "username") String username, @Param(value = "phone") String phone);

    @Update("update t_user t SET  t.pwd =#{pwd} WHERE t.name =#{username}")
    void updateUserPasswordByName(@Param(value = "username") String username, @Param(value = "pwd") String password);

    @Update("update t_user t SET  t.street =#{street} WHERE t.name =#{username}")
    int updateUserStreetdByName(@Param(value = "username") String username, @Param(value = "street") String street);

    @Select("Select t1.userid as userid, name as username,mobile as phone,email as email,  state as state, street as street,IP as IP,t1.createtime as createtime, t2.rolename as rolename,\n" +
            "t2.roleremark as roleremark FROM   t_user AS  t1   JOIN t_role AS t2 WHERE t1.roleid = t2.roleid")
    List<User> getUsers();
    @Select("Select * from t_user where userid = #{userid}")
    User getUSerByID(int userid);
}
