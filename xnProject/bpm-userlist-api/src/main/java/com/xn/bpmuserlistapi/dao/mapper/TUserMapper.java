package com.xn.bpmuserlistapi.dao.mapper;

import com.xn.bpmuserlistapi.dao.pojo.UsersAndAccountName;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zly123
 * @since 2019-05-20
 */
@Repository
@Mapper
public interface TUserMapper {

    @Select("select  username AS accountName, id AS accountId  from  accounts where id>=#{last_id} limit 10")
    List<UsersAndAccountName> findUserList (String lastId) ;

}
