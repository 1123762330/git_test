package com.xnpool.account.service;

import com.xnpool.account.entity.MillName;

import java.util.List;

public interface MillService {
    void setMill(String coin);
    void changeName(String name,String oldName,String user);
    void group(String user,Integer groupId,List indexs);

    void addGroup(String groupName,Integer userId);
    void delGroup(List<Integer> groupIds);

    List<MillName> selectGroup(Integer userId,String coin);

    List<MillName> selectGroupId(Integer groupId);

    MillName getNameByOldName(String oldName,String coin);


    /**
     * 通过userId 查询分组名
     * @param userId
     * @return
     */
    List<String> findGroupName(Integer userId);
}
