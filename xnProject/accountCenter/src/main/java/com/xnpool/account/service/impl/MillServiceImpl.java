package com.xnpool.account.service.impl;

import com.xnpool.account.entity.MillName;
import com.xnpool.account.mappers.MillMapper;
import com.xnpool.account.service.MillService;
import com.xnpool.account.service.exception.DataExistException;
import com.xnpool.account.service.exception.DataNotFoundException;
import com.xnpool.account.service.exception.InsertException;
import com.xnpool.account.service.exception.UpdateException;
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
                System.err.println(accountName+" "+millName);

            }
        }

    }

    @Override
    public void changeName(String name, String oldName, String accountName) {
        String newName = millMapper.findNewName(oldName);
        if (newName!=null){
            if (newName.equals(name)){
                throw new DataExistException("矿机名已存在!");
            }
        }
        updateName(oldName,name,accountName);
    }

    @Override
    public void group(String accountName, Integer groupId,List indexs) {
        List<Integer> list = findIdByUser(accountName);
        boolean result = list.containsAll(indexs);
        if(result){
            updateGroup(indexs, groupId);
        }else {
           throw new DataNotFoundException("你无分组权限,请登录在分组!");
        }
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
    public void delGroup(List<Integer> groupIds) {
        Integer rows = millMapper.delGroup(groupIds);
        if(rows < 1){
            throw new UpdateException("删除分组失败!");
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
}
