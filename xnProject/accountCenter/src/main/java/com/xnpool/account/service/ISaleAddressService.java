package com.xnpool.account.service;



import com.xnpool.account.entity.SaleAccountVO;
import com.xnpool.account.entity.SaleAddress;
import com.xnpool.account.entity.SaleAddressAll;

import java.util.List;

public interface ISaleAddressService {
    /**
     * 添加地址
     * @param
     * @return
     */
    void add(Integer accountId, String currency, String coinAddress);

    /**
     * 修改地址
     * @param
     * @return
     */
    void change(Integer id, String coinAddress, Integer userid);

    /**
     * 查询子账户下所有绑定钱包信息列表
     * @param accountId
     * @return
     */
    List<SaleAddress> getByAccountId(Integer accountId,Integer userId);
    /**
     * 查询子账户下指定币种的钱包信息
     * @param name
     * @param currency
     * @return
     */
    SaleAccountVO getByAidAndCurrency(String name, String currency);
    /**
     * 软删除:修改delete_at字段 null表示wei删除 时间表示删除时间
     * @param id
     * @return
     */
    void dropByid(Integer id, Integer userid);

    /**
     * 查询钱包权限信息
     * @return
     */
    List<SaleAddressAll> findSaleAddress ();

    /**
     * 修改钱包级别
     */
    void updateWalletRank (Integer rank,Integer walletId);

    /**
     * 修改数据前先回显数据
     * @param walletId
     * @return
     */
    SaleAddressAll selectWallet (Integer walletId);
}
