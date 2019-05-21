package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.*;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.model.SharesForAccountsModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

@Component
public interface SharesMapper extends Mapper<Shares> {
    Integer getLastInsertedShareId();

    @Select("SELECT" +
            "        a.id," +
            "        SUBSTRING_INDEX( s.username , '.', 1 ) AS username," +
            "        a.no_fees AS no_fees," +
            "        IFNULL(SUM(IF(our_result='Y', IF(s.difficulty=0, POW(2, (20 - 16)), s.difficulty), 0)), 0) AS valid," +
            "        IFNULL(SUM(IF(our_result='N', IF(s.difficulty=0, POW(2, (20 - 16)), s.difficulty), 0)), 0) AS invalid" +
            "        FROM shares AS s" +
            "        LEFT JOIN accounts AS a" +
            "        ON a.username = SUBSTRING_INDEX( s.username , '.', 1 )" +
            "        WHERE s.id >= #{previous_upstream} AND s.id <= #{current_upstream} AND a.is_locked != 2" +
            "        GROUP BY username DESC")
    List<SharesForAccountsModel> getSharesForAccounts(@Param(value = "previous_upstream") int previous_upstream, @Param(value = "current_upstream") int current_upstream);


    @Select("SELECT  " +
            "  SUBSTRING_INDEX( s.username , '.', 1 ) AS accoutName," +
            "  IFNULL(SUM(IF(our_result='Y', IF(s.difficulty=0, POW(2, (20 - 16)), s.difficulty), 0)), 0) AS valid," +
            "  IFNULL(SUM(IF(our_result='N', IF(s.difficulty=0, POW(2, (20 - 16)), s.difficulty), 0)), 0) AS invalid" +
            "  FROM shares AS s " +
            "  WHERE s.id >= #{previous_upstream} AND s.id <= #{current_upstream}" +
            "  GROUP BY accoutName DESC")
    List<SharesForAccountsModel> getSharesForAccount(@Param(value = "previous_upstream") int previous_upstream, @Param(value = "current_upstream") int current_upstream);





    List<Shares> selectAllShares();

    /**
     * 通过记录id查找已预结算的数据
     *
     * @param ppsId
     * @return
     */
    List<Shares> selectById(@Param("startId") Integer startId, @Param("ppsId") Integer ppsId);

    /**
     * 根据ilastId查询更新时间
     *
     * @param lastId
     * @return
     */
    String selectTimeById(Integer lastId);

    /**
     * 在history表通过时间段查询算力
     *
     * @param beforeTime
     * @param updateTime
     * @return
     */
    List<UsernameDifficulty> selectBYTime(@Param("beforeTime") Date beforeTime, @Param("updateTime") Date updateTime);

    /**
     * 在history表通过时间段查询有效算力
     *
     * @param beforeTime
     * @param updateTime
     * @return
     */
    List<UsernameDifficulty> selectValidByTime(@Param("beforeTime") Date beforeTime, @Param("updateTime") Date updateTime);

    /**
     * 查询24小时矿池算力
     *
     * @param zeroTime
     * @return
     */
    List<Difficulty> select24DifficultyByzeroTime(@Param("zeroTime") Date zeroTime, @Param("twentyfourTime") Date twentyfourTime);

    /**
     * 在shares中查询所有username
     *
     * @return
     */
    List<UserNameTime> selectUsername(@Param("startId") Integer startId, @Param("lastId") Integer lastId);

    /**
     * 查询用户小时算力
     *  改
     * @param beforeTime
     * @param updateTime
     * @return
     */
    List<UserDifficulty> selectUserDifficulty(@Param("beforeTime") Date beforeTime, @Param("updateTime") Date updateTime);


    /**
     * 查询矿池日平均算力,及总算力
     *
     * @param zeroTime
     * @param updateTime
     * @return
     */
    Double selectPoolAvgDifficulty(@Param("zeroTime") Date zeroTime, @Param("updateTime") Date updateTime);

    /**
     * 查询矿池在线矿机数量
     *
     * @param beforeTime
     * @param updateTime
     * @return
     */
    PoolDifficulty selectPoolWorks(@Param("beforeTime") Date beforeTime, @Param("updateTime") Date updateTime);

    /**
     * 将shares表已结算数据转移到history表
     *
     * @param startId
     * @param lastId
     * @return
     */
    Integer insertHistory(@Param("startId") Integer startId, @Param("lastId") Integer lastId);

    /**
     * 对Shares表进行数据转移到中间表
     *
     * @param lastId
     */
    Integer insertMiddle(@Param("startId") Integer startId, @Param("lastId") Integer lastId);

    /**
     * 根据lastId删除数据
     *
     * @param lastId
     * @return
     */
    Integer deleteById(Integer lastId);

    /**
     * 15分钟的平均算力
     *
     * @param
     * @param startDate
     * @param endDate
     * @return
     */
    List<Hashrate> avgHashrate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


    /**
     * 15分钟区间的真实算力
     *
     * @param realHashrateId
     * @return
     */
    Float findRealHashrate(Integer realHashrateId);

    /**
     * 查找一台矿机24小时的数据
     *
     * @param nowDate
     * @param beforeWeekDate
     * @return
     */
    List<WeekShares> findUserOneWorkerWeekData(@Param("nowDate") Date nowDate, @Param("beforeWeekDate") Date beforeWeekDate);

    /**
     * 查找一个用户下的所有矿机的平均算力
     *
     * @param startTime
     * @param endTime
     * @return
     */
    List<AllWorkerAverageForce> findUserAllWorkerAverageForce(@Param("startTime") Date startTime, @Param("endTime") Date endTime);


    /**
     * 查询shares表中所有的用户
     * @param startIndex
     * @param lastIndex
     * @return
     */
    List<UserCount> selectUser(@Param("startIndex") Integer startIndex, @Param("lastIndex") Integer lastIndex);
    /**
     * 通过user查询shares表中所有的username
     */
    List<String> getUsernameAndUser();
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
     * 查询一天的有效率
     * @param startDate
     * @param endDate
     */
    List<Valid> findUserValid (@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 查询一天的矿机有效率
     * @param startDate
     * @param endDate
     * @return
     */
    List<MillValid> findUsernameValid(@Param("startDate") Date startDate,@Param("endDate") Date endDate);

    /**
     * 查询矿机一小时数据
     * @param beforeTime
     * @param updateTime
     * @return
     */
    List<UsernameDifficulty> findOneHourMill(@Param("beforeTime") Date beforeTime, @Param("updateTime") Date updateTime);

    /**
     * 查询矿池总矿机数
     * @param day
     * @return
     */
    List<String> findUsernameByPool(Integer day);

    /**
     * 查询矿池总矿工数
     * @param day
     * @return
     */
    List<String>  findUserByPool(Integer day);

    /**
     * 打开定时
     * @return
     */
    Integer OpenTimer();

    /**
     * 查询在线矿机
     * @param startId
     * @param lastId
     * @return
     */
    List<String> getOnlineMill(@Param("startId") Integer startId, @Param("lastId") Integer lastId);
}