package com.xnpool.account.mappers;

import com.xnpool.account.entity.Authorinsing;
import com.xnpool.account.entity.ByAuthorising;
import com.xnpool.account.entity.Impower;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface ImpowerMapper {
    /**
     * 添加授权
     * @param impower
     * @return
     */
    Integer addImpower(Impower impower);

    /**
     * 30分钟时间一过,失效,将授权时间重置为0,授权状态改为1,未授权状态
     */
    void updateAuthorising (Integer id);

    /**
     * 如果用户已经授权过该用户
     * 那么再次授权时先进行查询
     * 有没有授权过该子账户给某人,是否已经失效
     * @return
     */
    Impower selectStatu (Impower impower);

    /**
     * 被授权方显示显示所有自己接受授权账户列表
     * @param byImpowerName
     * @return
     */
    List<ByAuthorising> selectByImpowerName (String byImpowerName);

    /**
     * 授权方授权给被授权用户前,首先需要判断这个被授权方存不存在
     * @param byImpowerName
     * @return
     */
    String selectUsers (String byImpowerName);

    /**
     * 查看此账户已经授权给了几个人
     * @param pramImpowerAid
     * @return
     */
    Integer selectAuthorisingCount (Integer pramImpowerAid);


    /**
     * 授权方显示被授权的用户名及权限
     * @param userId
     * @param impowerAid
     * @return
     */
    List<Authorinsing> selectByUserId(@Param("userId") Integer userId, @Param("impowerAid") Integer impowerAid);

    /**
     * 查询定时时间
     * @return
     */
    List<Impower> selectTime();

    /**
     * 已授权过用户更新授权时间
     * @param impower
     * @return
     */
    Integer updateAuthoris(Impower impower);
}
