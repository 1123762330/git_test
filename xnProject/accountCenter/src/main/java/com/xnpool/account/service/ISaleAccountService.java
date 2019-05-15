package com.xnpool.account.service;



import com.xnpool.account.entity.SaleAccount;
import com.xnpool.account.entity.UsersAndCoins;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ISaleAccountService {
    /**
     * 根据地址和币种获取用户名
     * @param coin
     * @param address
     * @return
     */
    String getUsernameByCoinAndAddress( String coin, String address);
    /**
     * 新增账户
     * @param saleAccount
     * @return
     */
    void add(SaleAccount saleAccount);
    /**
     * 修改账户
     * @param saleAccount
     * @return
     */
    void change(SaleAccount saleAccount);
    /**
     * 查询用户所有子账户列表
     * @param uid
     * @return
     */
    List<SaleAccount> getByUid(Integer uid);
    /**
     * 软删除:修改delete_at字段 null表示wei删除 时间表示删除时间
     * @param id
     * @return
     */
    void dropByid(Integer id, Integer userId);
    /**
     * 查询用户列表和用户对应的钱包列表
     * @param userId
     * @return
     */
    List<UsersAndCoins> selectUsersAndCoins (Integer userId);

    /**
     * 通过传入的币种和钱包地址判断有没有相应的数据
     * @param coin
     * @param address
     * @return
     */
    Integer findDataByAddress(String coin,String address);

    /**
     * 通过用户名查询用户Id
     * @param usersName
     * @return
     */
    Integer selectUsersId (String usersName);
}
