package com.xnpool.account.service.impl;

import com.xnpool.account.entity.MillName;
import com.xnpool.account.mappers.MillMapper;
import com.xnpool.account.service.MillService;
import com.xnpool.account.service.exception.*;
import com.xnpool.account.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class MillServiceImpl implements MillService {
    @Autowired
    MillMapper millMapper;
    @Autowired
    RedisUtil redisUtil;
    public void setMill(String coin){
        Set<String> keys = redisUtil.getSubKey(coin.toUpperCase()+":TOTAL:*");
        System.err.println(keys.size());
        for(String key:keys){
            Set<Object> strSet = redisUtil.sGet(key);
            for(Object str:strSet){
                String name = str.toString();
                String accountName = name.substring(0,name.lastIndexOf("."));
                String millName = name.substring(name.lastIndexOf(".")+1);
                List<String> userList = findByUser(accountName);
                if(userList.contains(millName)){
                    log.error("矿机已存在!");
                    continue;
                }
                insertMill(accountName,millName,millName,coin);
                log.info(accountName+" "+millName);

            }
        }

    }

    @Override
    public void changeName(String name, String oldName, String accountName,String coin) {
        String newName = millMapper.findNewName(oldName,coin);
        if (newName!=null){
            if (newName.equals(name)){
                throw new DataExistException("矿机名已存在!");
            }
        }
        updateName(oldName,name,accountName);
    }

    @Override
    public void group(String accountName, Integer groupId,List indexs,Integer userId) {
        Object id = indexs.get(0);
        Integer data = millMapper.selectUserId(Integer.valueOf(id.toString()));
        if(data==null){
            throw new DataNotFoundException("你没有权限移动分组，或为登录");
        }
        if(data!=userId){
            throw new NotActiveException("你没有权限移动分组");
        }
        //通过id查询子账户名
        updateGroup(indexs, groupId);
    }
    /**
     * 分组
     * @param indexs
     * @return
     */
    private void updateGroup(List<Integer> indexs,Integer groupId){
        Integer row = millMapper.updateGroup(indexs,groupId);
        if(row <1){
            throw new UpdateException("更新矿机时出现未知错误!");
        }
    }
    private void insertMill(String accountName,String name, String oldName, String coin){
         Integer row = millMapper.insertMill(accountName,name,oldName,coin);
         if(row != 1){
            throw new InsertException("添加矿机时出现未知错误!");
         }
    }
    private List<String> findByUser(String accountName){
        return millMapper.findOldNameByUser(accountName);
    }
    /**
     * 通过用户名查id
     * @param user
     * @return
     */
    private List<Integer> findIdByUser(String user){
        return millMapper.findIdByUser(user);
    }
    /**
     * 通过矿机原名修改矿机别名
     * @param oldName
     * @param name
     * @return
     */
    private void updateName(String oldName, String name, String accountName){
        Integer rows = millMapper.updateName(oldName, name, accountName);
        if(rows != 1){
            throw new UpdateException("更新矿机名出错!");
        }
    }



    /**
     * 添加分组
     * @param groupName
     */
    @Override
    public void addGroup(String groupName,Integer userId) {
        Integer rows = millMapper.addGroup(groupName,userId);
        if(rows != 1){
            throw new UpdateException("添加分组失败!");
        }
    }

    /**
     * 删除分组
     * @param groupIds
     */
    @Override
    public void delGroup(List<String> groupIds,Integer userId) {
        Integer id = Integer.valueOf(groupIds.get(0));
        Integer integer = millMapper.selectUserId(id);
        if(userId==integer){
            Integer rows = millMapper.delGroup(groupIds);
            if(rows < 1){
                throw new UpdateException("删除分组失败!");
            }
        }
    }

    /**
     * 查询所有矿机
     * @return
     */
    @Override
    public List<MillName> selectGroup(Integer userId,String coin) {
        return millMapper.selectGroup(userId,coin);
    }

    /**
     * 查询矿机分组
     * @param groupId
     * @return
     */
    @Override
    public List<MillName> selectGroupId(Integer groupId) {
        return millMapper.selectGroupId(groupId);
    }

    /**
     * 通过原名查改名
     * @param oldName
     * @return
     */
    @Override
    public MillName getNameByOldName(String oldName,String coin) {
        return millMapper.getNameByOldName(oldName,coin);
    }

    @Override
    public List<String> findGroupName(Integer userId) {
        return millMapper.findGroupName(userId);
    }

    @Override
    public String findNewName(String oldName,String coin) {
        return millMapper.findNewName(oldName,coin);
    }


}
