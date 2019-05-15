package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.TransacTions;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.daily.DailyVO;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.payout.TransactionsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;


import java.util.List;

@Component
public interface TransacTionsMapper extends Mapper<TransacTions> {
    /**
     *获取已经日结，未支付记录
     * @param type "Debit_Daily"
     * @return 交易列表
     */
    List<TransacTions> getDailyQueue(String type);

    /**
     *使用RPC事务ID更新事务
     * @param transactionId id整数交易ID
     * @param rpcTxid txid字符串RPC事务标识符
     * @return num 或 -1（假）
     */
    Integer setRPCTxId(@Param("transactionId") Integer transactionId, @Param("rpcTxid") String rpcTxid);

    /**
     * 获取帐户总余额，忽略已存档的条目
     * @param accountId 帐户ID
     * @return  借记 - 费用 - 捐款
     */
    TransactionsVO getBalance(@Param("accountId") Integer accountId, @Param("confirmations") Integer confirmations);

    /**
     * 在我们的类表中添加一个新事务
     * 我们还存储插入的ID，以备用户需要时使用
     * @return
     */
     Integer addTransaction(TransacTions transactions);

    /**
     * 将用户的交易标记为已存档
     * @return
     */
     Integer setArchived(@Param("accountId") Integer accountId,@Param("id") Integer id);

    /**
     * 获取我们的自动支付队列
     * @param limit
     * @return 数据数组帐户设置和确认余额
     */
    List<DailyVO> getAPQueue(@Param("limit") Integer limit, @Param("confirmed") Double confirmed);

    /**
     * 更新所有已付费的每日交易记录
     * @param dailyId
     * @param id
     * @param txid
     * @return
     */
    Integer setArchivedForDebit_Daily(@Param("dailyId") Integer dailyId,@Param("id")Integer id,@Param("txid")Integer txid);

    /**
     * 通过类型找出支付数据
      * @param type
     * @param accountId
     * @return
     */
    Double findbyType(@Param("type") String type,@Param("accountId") Integer accountId);


}