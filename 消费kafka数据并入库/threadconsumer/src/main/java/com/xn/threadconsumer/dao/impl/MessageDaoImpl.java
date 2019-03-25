package com.xn.threadconsumer.dao.impl;

import com.xn.threadconsumer.dao.MessageDao;
import com.xn.threadconsumer.domain.PoolWorker;
import com.xn.threadconsumer.util.JDBCUtils;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class MessageDaoImpl implements MessageDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public List<PoolWorker> findMessageList() {
        return null;
    }

    @Override
    public void saveMessage(Integer userId, String username) {
        //1.定义sql
        String sql = "insert into pool_worker(account_id,username) values(?,?)";
        //2.执行sql
        template.update(sql, userId,username);
    }

    /*@Override
    public void saveMessage(PoolWorker poolWorker) {
        //1.定义sql
        String sql = "insert into pool_worker(account_id,username) values(?,?)";
        //2.执行sql
        template.update(sql, poolWorker.getUser_id(),poolWorker.getUser_name());
        //System.out.println("用户id是"+poolWorker.getUser_id()+",用户名是"+poolWorker.getUser_name());
    }*/
}
