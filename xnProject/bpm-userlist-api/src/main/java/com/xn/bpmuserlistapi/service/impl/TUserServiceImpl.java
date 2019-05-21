package com.xn.bpmuserlistapi.service.impl;

import com.xn.bpmuserlistapi.dao.mapper.TUserMapper;
import com.xn.bpmuserlistapi.dao.pojo.QueryResult;

import com.xn.bpmuserlistapi.dao.pojo.UsersAndAccountName;
import com.xn.bpmuserlistapi.service.TUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zly123
 * @since 2019-05-20
 */
@Service
@Slf4j
public class TUserServiceImpl  implements TUserService {
    @Autowired
    private TUserMapper tUserMapper;

    public QueryResult findUserListApi(String lastId) {
        try {
            //创建map集合用来封装data数据
            Map<String, Integer> data = new LinkedHashMap<>();
            //如果为空，默认从1开始查询
            if (lastId == null || lastId.equals("")) {
                List<UsersAndAccountName> UsersAndAccountNames = tUserMapper.findUserList("1");
                bianli(UsersAndAccountNames,data);
                return new QueryResult(0, null, data);
            }

            //如果传入参数，执行此方法
            List<UsersAndAccountName> UsersAndAccountNames = tUserMapper.findUserList(lastId);
            if (UsersAndAccountNames.size() == 0) {
                return new QueryResult<ArrayList>(0, null, new ArrayList());
            } else {
                bianli(UsersAndAccountNames,data);
                return new QueryResult(0, null, data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //遍历数据，取出key,Value
    public void bianli(List<UsersAndAccountName> UsersAndAccountNames ,Map<String, Integer> data ){
        for (int i = 0; i < UsersAndAccountNames.size(); i++) {
            String accountName = UsersAndAccountNames.get(i).getAccountName();
            Integer userId = UsersAndAccountNames.get(i).getAccountId();
            data.put(accountName, userId);
        }
    }

}
