package com.xnpool.account.service.impl;


import com.xnpool.account.entity.SaleAccount;
import com.xnpool.account.entity.SaleAddress;
import com.xnpool.account.entity.UsersAndCoins;
import com.xnpool.account.mappers.SaleAccountMapper;
import com.xnpool.account.service.ISaleAccountService;
import com.xnpool.account.service.ISaleAddressService;
import com.xnpool.account.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SaleAccountServiceImpl implements ISaleAccountService {
    @Autowired
    SaleAccountMapper saleAccountMapper;
    @Autowired
    ISaleAddressService saleAddressService;

    @Override
    public String getUsernameByCoinAndAddress(String coin, String address) {
        return saleAccountMapper.getUsernameByCoinAndAddress(coin,address);
    }

    @Override
    public void add(SaleAccount saleAccount) {
         SaleAccount data = selectByName(saleAccount.getName(),saleAccount.getUserId());
         if(data != null){
            throw new DataExistException("用户名已存在!或你还未登录!");
         }
         saleAccount.setCreatedAt(new Date());
         insert(saleAccount);
    }

    @Override
    public void change(SaleAccount saleAccount) {
        SaleAccount data = selectById(saleAccount.getId());
        if(data == null){
            throw new DataNotFoundException("该子账户或已经删除!");
        }
        if(saleAccount.getUserId()!=data.getUserId()){
            throw new NotActiveException("你无权修改子账户!请登录!");
        }
        saleAccount.setUpdatedAt(new Date());
        update(saleAccount);
    }

    @Override
    public List<SaleAccount> getByUid(Integer uid) {
        List<SaleAccount> list = selectByUid(uid);
        if(list.size()==0||list==null){
            throw new DataNotFoundException("还没有创建子账户!");
        }
        return list;
    }

    @Override
    public void dropByid(Integer id,Integer userId) {
        SaleAccount data = selectById(id);
        if(data == null){
            throw new DataNotFoundException("该子账户或已经删除!");
        }
        if(data.getUserId()!=userId){
            throw new NotActiveException("你无权删除子账户!请登录!");
        }
        List<SaleAddress> data1  = saleAddressService.getByAccountId(id);
        if(!data1.isEmpty()){
            throw new DataExistException("请先删除该账户下的所有地址!子账户名:"+data.getName());
        }
        deleteByid(id);
    }

    /**
     * 新增账户
     * @param saleAccount
     * @return
     */
    private void insert(SaleAccount saleAccount){
        Integer rows = saleAccountMapper.insert(saleAccount);
        if(rows != 1){
            throw new InsertException("新增子账户信息时出现未知错误!未能新增成功");
        }
    }

    /**
     * 修改账户
     * @param saleAccount
     * @return
     */
    private void update(SaleAccount saleAccount){
        Integer rows = saleAccountMapper.update(saleAccount);
        if(rows !=1){
            throw new UpdateException("修改子账户信息时出现未知错误!未能修改成功!");
        }
    }

    /**
     * 通过id查询子账户
     * @param id
     * @return
     */
    private SaleAccount selectById(Integer id){
        return saleAccountMapper.selectById(id);
    }

    /**
     * 查询用户所有子账户列表
     * @param uid
     * @return
     */
    private List<SaleAccount> selectByUid(Integer uid){
        return saleAccountMapper.selectByUid(uid);
    }
    /**
     * 通过用户名查询
     * @param name
     * @return
     */
    private SaleAccount selectByName(String name,Integer userId){
        return saleAccountMapper.selectByName(name,userId);
    }
    /**
     * 软删除:修改delete_at字段 null表示wei删除 时间表示删除时间
     * @param id
     * @return
     */
    private void deleteByid(Integer id){
        Integer rows = saleAccountMapper.deleteByid(id,new Date());
        if(rows != 1){
            throw new DeleteException("删除子账户信息时出现未知错误!未能删除!");
        }
    }

    /**
     * 通过用户查询子账号列表和对应的币种列表
     * @param userId
     * @return
     */
    @Override
    public List<UsersAndCoins> selectUsersAndCoins (Integer userId) {
        return saleAccountMapper.selectUsersAndCoins(userId);
    }

    /**
     *通过传入的币种和钱包地址判断有没有相应的数据
     * @param coin
     * @param address
     * @return
     */
    @Override
    public Integer findDataByAddress (String coin, String address) {
        return saleAccountMapper.findDataByAddress(coin,address);
    }
    /**
     * 通过用户名查询用户Id
     * @param usersName
     * @return
     */
    @Override
    public Integer selectUsersId (String usersName) {
        return saleAccountMapper.selectUsersId(usersName);
    }
}
