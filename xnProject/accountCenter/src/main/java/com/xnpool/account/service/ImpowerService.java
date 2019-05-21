package com.xnpool.account.service;

import com.xnpool.account.entity.Authorinsing;
import com.xnpool.account.entity.ByAuthorising;
import com.xnpool.account.entity.Impower;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zly
 * @Date: 2019/5/15 17:54
 */
@Service
public interface ImpowerService {
    void addAuthorising (Impower impower);

    void updateAuthorising (Integer id);

    Impower selectStatu (Impower impower);

    List<ByAuthorising> selectByImpowerName (String byImpowerName);

    String selectUsers (String byImpowerName);

    /**
     * 授权方显示被授权的用户名及权限
     * @param userId
     * @param impowerAid
     * @return
     */
    List<Authorinsing> selectByUserId(@Param("userId") Integer userId, @Param("impowerAid") Integer impowerAid);

    Integer selectAuthorisingCount (Integer pramImpowerAid);
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
