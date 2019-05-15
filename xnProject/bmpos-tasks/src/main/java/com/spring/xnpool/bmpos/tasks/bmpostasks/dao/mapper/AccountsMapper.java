package com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.domain.Accounts;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

@Component
public interface AccountsMapper extends Mapper<Accounts> {
    /**
     * 通过用户名得到email
     *
     * @param accountId
     * @return
     */
    String getUserEmailById(Integer accountId);

    @Select("SELECT * FROM accounts WHERE username = #{userName}")
    Accounts queryAccountByUserName(String userName);
}