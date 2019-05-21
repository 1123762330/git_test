package com.xnpool.account.mappers;

import com.xnpool.account.entity.MillName;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MillMapper {
   /**
    * 从缓存里获取矿机存入数据库
    * @param accountName
    * @param name
    * @param oldName
    * @return
    */
   Integer insertMill(@Param("accountName") String accountName,@Param("name") String name, @Param("oldName") String oldName,@Param("coin") String coin);

   /**
    * 通过用户查询矿机
    * @param accountName
    * @return
    */
   List<String> findOldNameByUser(String accountName);

   /**
    * 通过用户名查id
    * @param accountName
    * @return
    */
   List<Integer> findIdByUser(String accountName);
   /**
    * 通过矿机原名修改矿机别名
    * @param oldName
    * @param name
    * @return
    */
   Integer updateName(@Param("oldName") String oldName,@Param("name") String name,@Param("accountName") String accountName);

   /**
    * 分组
    * @param indexs
    * @return
    */
   Integer updateGroup(@Param("indexs") List indexs,@Param("groupId") Integer groupId);

   /**
    * 添加分组
    * @param groupName
    */
   Integer addGroup(@Param("groupName") String groupName,@Param("userId") Integer userId);

   /**
    * 删除分组
    * @param groupIds
    * @return
    */
   Integer delGroup(@Param("groupIds")List<String> groupIds);

   /**
    * 查询所有矿机
    * @return
    */
   List<MillName> selectGroup(@Param("userId") Integer userId,@Param("coin") String coin);

   /**
    * 查询矿机分组
    * @param groupId
    * @return
    */
   List<MillName> selectGroupId(Integer groupId);

   /**
    * 通过原名查更改名
    * @param oldName
    * @return
    */
   MillName getNameByOldName(@Param("oldName") String oldName,@Param("coin") String coin);

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
   String findNewName(@Param("oldName") String oldName,@Param("coin") String coin);

   /**
    * 用过id查询用户id
    * @param id
    * @return
    */
   Integer selectUserId(Integer id);
}
