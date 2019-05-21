package com.spring.xnpool.bmpos.tasks.bmpostasks.service.impl;


import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.*;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.SharesMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.SharesForAccountsModel;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.ShareService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Slf4j

public class ShareServiceImpl implements ShareService {
    @Autowired
    SharesMapper sharesMapper;

    @Override
    public List<Shares> querAllShares() {
        return sharesMapper.selectAllShares();
    }

    @Override
    public Integer getLastInsertedShareId() {
        Integer sharesId = sharesMapper.getLastInsertedShareId();

        if (sharesId == null) {
            sharesId = 0;
        }
        return sharesId;
    }


    public List<SharesForAccountsModel> getSharesForAccounts(int previous_upstream, int current_upstream) {
        List<SharesForAccountsModel> sharesForAccountsModels = sharesMapper.getSharesForAccount(previous_upstream, current_upstream);
        return sharesForAccountsModels;
    }


    @Override
    public List<Shares> getByppsId(Integer startId, Integer ppsId) {
        return selectById(startId, ppsId);
    }

    @Override
    public List<UsernameDifficulty> getBytimeAndUsername(Date beforeTime, Date updateTime) {
        return sharesMapper.selectBYTime(beforeTime, updateTime);
    }

    @Override
    public List<UsernameDifficulty> getValidByTime(Date beforeTime, Date updateTime) {
        return sharesMapper.selectValidByTime(beforeTime, updateTime);
    }

    @Override
    public List<UserDifficulty> selectUserDifficulty(Date beforeTime, Date updateTime) {
        return  sharesMapper.selectUserDifficulty(beforeTime, updateTime);

    }

    @Override
    public List<Difficulty> select24DifficultyByzeroTime(Date zeroTime, Date twentyfourTime) {
        return sharesMapper.select24DifficultyByzeroTime(zeroTime, twentyfourTime);
    }

    @Override
    public PoolDifficulty selectPoolWorks(Date beforeTime, Date updateTime) {
        return sharesMapper.selectPoolWorks(beforeTime, updateTime);
    }

    /**
     * 将shares已结算表数据转移到sharehistory表中同时删除原表数据
     *
     * @param startId
     * @param lastId
     */
    @Override
    @Transactional
    public void dumpHistory(Integer startId, Integer lastId) {
        insertHistoryById(startId, lastId);
        deleteById(lastId);
    }

    /**
     * 对Shares表进行数据转移到中间表
     *
     * @param lastId
     */
    @Override
    public void insertMiddle(Integer startId, Integer lastId) {
        sharesMapper.insertMiddle(startId,lastId);
    }

    @Override
    public Double selectPoolAvgDifficulty(Date zeroTime, Date updateTime) {
        return sharesMapper.selectPoolAvgDifficulty(zeroTime, updateTime);
    }

    @Override
    public List<UserNameTime> selectUsername(Integer startId, Integer lastId) {
        return sharesMapper.selectUsername(startId, lastId);
    }

    @Override
    public String getTimeById(Integer lastId) {
        return sharesMapper.selectTimeById(lastId);
    }

    /**
     * 通过记录id查找已预结算的数据
     *
     * @param ppsId
     * @return
     */
    List<Shares> selectById(Integer startId, Integer ppsId) {
        return sharesMapper.selectById(startId, ppsId);
    }

    private void insertHistoryById(Integer startId, Integer lastId) {
        Integer rows = sharesMapper.insertHistory(startId, lastId);
        if (rows < 0) {
            log.error("从shares表中转移已结算数据失败!");
            return;
        }
    }

    private void deleteById(Integer lastId) {
        Integer rows = sharesMapper.deleteById(lastId);
        if (rows < 0) {
            log.error("从shares表中转移已结算数据失败!");
            return;
        }
    }

    /**
     * 查询shares表中所有的用户
     * @param startIndex
     * @param lastIndex
     * @return
     */
    @Override
    public List<UserCount> selectUser(Integer startIndex, Integer lastIndex) {
        return sharesMapper.selectUser(startIndex,lastIndex);
    }

    /**
     * 根据时间去判断需不需要转移数据
     * @return
     */
    @Override
    public List<Date> selectMidDay() {
        return sharesMapper.selectMidDay();
    }

    @Override
    public List<String> selectusernameByDay(Integer day) {
        return sharesMapper.selectusernameByDay(day);
    }

    @Override
    public List<MillValid> findUsernameValid(Date startDate, Date endDate) {
        return sharesMapper.findUsernameValid(startDate,endDate);
    }

    @Override
    public List<UsernameDifficulty> findOneHourMill(Date beforeTime, Date updateTime) {
        return sharesMapper.findOneHourMill(beforeTime,updateTime);
    }

    @Override
    public List<String> getOnlineMill(Integer startId, Integer lastId) {
        return sharesMapper.getOnlineMill(startId, lastId);
    }

}
