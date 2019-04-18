package com.xn.find_xn_user.dao;

import com.xn.find_xn_user.domain.Accounts;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface UserListDao {
    /**
     * 根据last_id进行条数查询
     * @param last_id
     * @return
     */
    public List<Accounts> findUserList(String last_id) ;

    /**
     * 通过用户名查询id
     * @param username
     * @return
     */
    Integer findUsernameById(String username);


    /**
     * 查找所有的用户
     * @return
     */
    List findAllUser();

    /**
     * 查找所有矿机列表
     * @return
     */
    List findAllPoolWorker();

    /**
     * 不存在的矿机就入库
     * @param accountId
     * @param username
     */
    void savePoolWorker(@Param("accountId") Integer accountId,@Param("username") String username);


    Accounts selectUser(String username);
}
