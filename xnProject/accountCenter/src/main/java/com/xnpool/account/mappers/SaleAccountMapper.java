package com.xnpool.account.mappers;

import com.xnpool.account.entity.SaleAccount;
import com.xnpool.account.entity.UsersAndCoins;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;


@Component
public interface SaleAccountMapper {
    /**
     * 新增账户
     * @param saleAccount
     * @return
     */
     Integer insert(SaleAccount saleAccount);

    /**
     * 修改账户
     * @param saleAccount
     * @return
     */
     Integer update(SaleAccount saleAccount);

    /**
     * 通过id查询子账户
     * @param id
     * @return
     */
     SaleAccount selectById(Integer id);

    /**
     * 查询用户所有子账户列表
     * @param uid
     * @return
     */
    List<SaleAccount> selectByUid(Integer uid);

    /**
     * 根据地址和币种获取用户名
     * @param coin
     * @param address
     * @return
     */
    String getUsernameByCoinAndAddress(@Param("coin") String coin,@Param("address") String address);
    /**
     * 通过用户名查询
     * @param name
     * @return
     */
    SaleAccount selectByName(@Param("name") String name,@Param("userId") Integer userId);

    /**
     * 软删除:修改delete_at字段 null表示wei删除 时间表示删除时间
     * @param id
     * @return
     */
    Integer deleteByid(@Param("id") Integer id, @Param("time") Date time);
    /**
     * 通过用户查询子账号列表和对应的币种列表
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
    Integer findDataByAddress(@Param("coin") String coin, @Param("address") String address);

    /**
     * 通过用户名查询用户Id
     * @param usersName
     * @return
     */
    Integer selectUsersId (String usersName);
}
