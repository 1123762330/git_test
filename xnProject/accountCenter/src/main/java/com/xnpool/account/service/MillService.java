package com.xnpool.account.service;

import com.xnpool.account.entity.MillName;

import java.util.List;

public interface MillService {
    void setMill(String coin);
    void changeName(String name,String oldName,String user,String coin);
    void group(String user,Integer groupId,List indexs,Integer userId);

    void addGroup(String groupName,Integer userId);
    void delGroup(List<String> groupIds,Integer userId);

    List<MillName> selectGroup(Integer userId,String coin);

    List<MillName> selectGroupId(Integer groupId);

    MillName getNameByOldName(String oldName,String coin);


    /**
     * 通过userId 查询分组名
     * @param userId
     * @return
     */
    List<String> findGroupName(Integer userId);


    /**
     * 通过原名查新名
     * @param oldName
     * @return
     */
    String findNewName(String oldName,String coin);
}
