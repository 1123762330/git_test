package com.xnpool.account.mappers;


import com.xnpool.account.entity.SaleAccountVO;
import com.xnpool.account.entity.SaleAddress;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public interface SaleAddressMapper {
    /**
     * 添加地址
     * @param saleAddress
     * @return
     */
    Integer insert(SaleAddress saleAddress);

    /**
     * 修改地址
     * @param
     * @return
     */
    Integer update(@Param("id") Integer id,@Param("coinAddress") String  coinAddress,@Param("updatedAt") Date updatedAt);

    /**
     * 根据币种查询
     * @param currency
     * @return
     */
    SaleAddress selectByC(String currency);
    /**
     * 通过id查询
     * @param id
     * @return
     */
    SaleAccountVO selectById(Integer id);
    /**
     * 查询子账户下所有绑定钱包信息列表
     * @param accountId
     * @return
     */
    List<SaleAddress> selectByAccountId(Integer accountId);
    /**
     * 查询子账户下指定币种的钱包信息
     * @param name
     * @param currency
     * @return
     */
    SaleAccountVO seletcByAidAndCurrency(
            @Param("name") String name,
            @Param("currency") String currency);

    /**
     *
     * @param accountId
     * @param currency
     * @return
     */
    SaleAccountVO selectByCidAndCurrency( @Param("accountId") Integer accountId, @Param("currency") String currency);

    /**
     * 软删除:修改delete_at字段 null表示wei删除 时间表示删除时间
     * @param id
     * @return
     */
    Integer deleteByid(@Param("id") Integer id, @Param("time") Date time);

}


