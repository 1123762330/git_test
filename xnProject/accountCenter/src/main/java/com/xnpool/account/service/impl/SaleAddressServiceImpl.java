package com.xnpool.account.service.impl;


import com.xnpool.account.entity.SaleAccountVO;
import com.xnpool.account.entity.SaleAddress;
import com.xnpool.account.mappers.SaleAddressMapper;
import com.xnpool.account.service.ISaleAddressService;
import com.xnpool.account.service.exception.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SaleAddressServiceImpl implements ISaleAddressService {
    @Autowired
    SaleAddressMapper saleAddressMapper;
    @Override
    public void add(Integer accountId,String currency,String coinAddress,String name) {
        //todo
        SaleAccountVO data = selectByCidAndCurrency(accountId,currency);
        if(data != null){
            throw new DataExistException(currency+"币钱包地址已存在!请不要重复添加!");
        }

        SaleAddress saleAddress = new SaleAddress();
        saleAddress.setCreatedAt(new Date());
        saleAddress.setCurrency(currency);
        saleAddress.setCoinAddress(coinAddress);
        saleAddress.setAccountId(accountId);
        insert(saleAddress);

    }

    @Override
    public void change(Integer id,String coinAddress,Integer userid) {
        SaleAccountVO data = selectById(id);
        if(data == null){
            throw new DataNotFoundException("币地址不存在!");
        }
        if(data.getUserId()!=userid){
            throw new NotActiveException("你无权修改地址!请登录!");
        }
        update(id,coinAddress,new Date());
    }

    @Override
    public List<SaleAddress> getByAccountId(Integer accountId) {
        List<SaleAddress> list = selectByAccountId(accountId);
        if(list.size()==0||list==null){
            throw new DataNotFoundException("还没有创建地址!");
        }
        return list;
    }

    @Override
    public SaleAccountVO getByAidAndCurrency(String name, String currency) {
        SaleAccountVO saleAccountVO = seletcByAidAndCurrency(name,currency);
        if(saleAccountVO==null){
            throw new DataNotFoundException("还没有创建"+currency+"币地址!");
        }
        return saleAccountVO;
    }

    @Override
    public void dropByid(Integer id,Integer userid) {
        SaleAccountVO data = selectById(id);
        if(data == null){
            throw new DataNotFoundException("要删除的数据不存在!");
        }
        if(data.getUserId()!= userid){
            throw new NotActiveException("你无权删除地址!请登录!");
        }
        deleteByid(id);
    }
    /**
     * 添加地址
     * @param saleAddress
     * @return
     */
    private void insert(SaleAddress saleAddress){
       Integer rows = saleAddressMapper.insert(saleAddress);
       if(rows !=1){
           throw new InsertException("添加地址时出现未知错误!未能添加!");
       }
    }

    /**
     * 修改地址
     * @param
     * @return
     */
    private void update(Integer id, String  coinAddress, Date updatedAt){
        Integer rows = saleAddressMapper.update(id,coinAddress,updatedAt);
        if(rows != 1){
            throw new UpdateException("修改数据时出现未知错误!未能修改!");
        }
    }
    /**
     * 通过id查询
     * @param id
     * @return
     */
    SaleAccountVO selectById(Integer id){
        return saleAddressMapper.selectById(id);
    }
    /**
     * 查询子账户下所有绑定钱包信息列表
     * @param accountId
     * @return
     */
    private List<SaleAddress> selectByAccountId(Integer accountId){
        return saleAddressMapper.selectByAccountId(accountId);
    }

    /**
     * 根据币种查询
     * @param currency
     * @return
     */
    SaleAddress selectByC(String currency){
        return  saleAddressMapper.selectByC(currency);
    }
    /**
     * 查询子账户下指定币种的钱包信息
     * @param name
     * @param currency
     * @return
     */
    private SaleAccountVO seletcByAidAndCurrency(String name, String currency){
        return saleAddressMapper.seletcByAidAndCurrency(name,currency);
    }

    /**
     * 软删除:修改delete_at字段 null表示wei删除 时间表示删除时间
     * @param id
     * @return
     */
    private void deleteByid(Integer id){
        Integer rows = saleAddressMapper.deleteByid(id,new Date());
        if(rows != 1){
            throw new DeleteException("删除地址时出现未知错误!未能删除!");
        }
    }
    private SaleAccountVO selectByCidAndCurrency(Integer accountId,String coin){
       return saleAddressMapper.selectByCidAndCurrency(accountId,coin);
    }
}
