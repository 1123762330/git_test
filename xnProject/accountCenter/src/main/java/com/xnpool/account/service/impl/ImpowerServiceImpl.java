package com.xnpool.account.service.impl;

import com.xnpool.account.entity.Authorinsing;
import com.xnpool.account.entity.ByAuthorising;
import com.xnpool.account.entity.Impower;
import com.xnpool.account.mappers.ImpowerMapper;
import com.xnpool.account.service.ImpowerService;
import com.xnpool.account.service.exception.DataNotFoundException;
import com.xnpool.account.service.exception.InsertException;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: zly
 * @Date: 2019/5/15 17:54
 */
@Service
public class ImpowerServiceImpl implements ImpowerService {
    @Autowired
    private ImpowerMapper impowerMapper;
    @Override
    public void addAuthorising (Impower impower) {
        Integer rows = impowerMapper.addImpower(impower);
        if(rows!=1){
            throw new InsertException("添加矿机时出现未知错误!");
        }
    }

    @Override
    public void updateAuthorising (Integer id) {
        impowerMapper.updateAuthorising(id);
    }

    @Override
    public Impower selectStatu (Impower impower) {
        return impowerMapper.selectStatu(impower);
    }

    @Override
    public List<ByAuthorising> selectByImpowerName (String byImpowerName) {
        return impowerMapper.selectByImpowerName(byImpowerName);
    }

    @Override
    public String selectUsers (String byImpowerName) {
        return impowerMapper.selectUsers(byImpowerName);
    }

    @Override
    public Integer selectAuthorisingCount (Integer pramImpowerAid) {
        return impowerMapper.selectAuthorisingCount(pramImpowerAid);
    }
    /**
     * 授权方显示被授权的用户名及权限
     * @param userId
     * @param impowerAid
     * @return
     */
    public List<Authorinsing> selectByUserId(@Param("userId") Integer userId, @Param("impowerAid") Integer impowerAid){
        List<Authorinsing> authorinsings = impowerMapper.selectByUserId(userId, impowerAid);
        if(authorinsings.isEmpty()){
            throw new DataNotFoundException("你还没有授权给任何人！");
        }
        return authorinsings;
    }

    @Override
    public List<Impower> selectTime() {
        return impowerMapper.selectTime();
    }

    @Override
    public Integer updateAuthoris(Impower impower) {
        return impowerMapper.updateAuthoris(impower);
    }


}
