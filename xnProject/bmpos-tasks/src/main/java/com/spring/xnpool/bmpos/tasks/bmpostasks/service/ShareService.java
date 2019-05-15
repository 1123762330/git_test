package com.spring.xnpool.bmpos.tasks.bmpostasks.service;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.*;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.SharesForAccountsModel;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface ShareService {
    //查询所有shares 数据
    List<Shares> querAllShares();


    Integer getLastInsertedShareId();

    List<SharesForAccountsModel> getSharesForAccounts(int previous_upstream, int current_upstream);

    List<Shares> getByppsId(Integer startId,Integer ppsId);

    /**
     * 在history表通过时间段查询算力
     * @param beforeTime
     * @param updateTime
     * @return
     */
    List<UsernameDifficulty> getBytimeAndUsername(Date beforeTime,Date updateTime);

    /**
     * 在history表通过时间段查询有效算力
     * @param beforeTime
     * @param updateTime
     * @return
     */
    List<UsernameDifficulty> getValidByTime(Date beforeTime,Date updateTime);

    /**
     * 查询用户小时算力
     * @param beforeTime
     * @param updateTime
     * @return
     */
    List<UserDifficulty> selectUserDifficulty(Date beforeTime, Date updateTime);
    /**
     * 查询24小时矿池算力
     * @param zeroTime
     * @param twentyfourTime
     * @return
     */
    List<Difficulty> select24DifficultyByzeroTime(Date zeroTime, Date twentyfourTime);
    /**
     * 查询矿池在线矿机数量
     * @param beforeTime
     * @param updateTime
     * @return
     */
    PoolDifficulty selectPoolWorks(Date beforeTime,Date updateTime);
    /**
     * 将shares已结算表数据转移到sharehistory表中同时删除原表数据
     * @param startId
     * @param lastId
     */
    void  dumpHistory(Integer startId,Integer lastId);

    /**
     * 将shares已结算表数据转移到sharemidder表中同时删除原表数据
     * @param startId
     * @param lastId
     */
    void insertMiddle(Integer startId,Integer lastId);
    /**
     * 查询矿池日平均算力,及总算力
     * @param zeroTime
     * @param updateTime
     * @return
     */
    Double selectPoolAvgDifficulty(Date zeroTime,Date updateTime);
    /**
     * 在history中查询所有username
     * @return
     */
    List<UserNameTime> selectUsername(Integer startId,Integer lastId);
    /**
     * 根据id查找最新更新时间
     * @param lastId
     * @return
     */
    String getTimeById(Integer lastId);

    /**
     * 查询shares表中所有的用户
     * @param startIndex
     * @param lastIndex
     * @return
     */
    List<UserCount> selectUser(Integer startIndex, Integer lastIndex);

    /**
     * 根据时间去判断需不需要转移数据
     * @return
     */
    List<Date> selectMidDay();
    /**
     * 查询最近七天的矿机
     * @param day
     * @return
     */
    List<String> selectusernameByDay(Integer day);

    /**
     * 查询一天的矿机有效率
     * @param startDate
     * @param endDate
     * @return
     */
    List<MillValid> findUsernameValid(Date startDate,Date endDate);

    /**
     * 查询矿机一小时数据
     * @param beforeTime
     * @param updateTime
     * @return
     */
    List<UsernameDifficulty> findOneHourMill(@Param("beforeTime") Date beforeTime, @Param("updateTime") Date updateTime);

}
