package com.xnpool.account.service;



import com.xnpool.account.entity.SaleAccountVO;
import com.xnpool.account.entity.SaleAddress;

import java.util.List;

public interface ISaleAddressService {
    /**
     * 添加地址
     * @param
     * @return
     */
    void add(Integer accountId, String currency, String coinAddress,String name);

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
    List<SaleAddress> getByAccountId(Integer accountId);
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
}
