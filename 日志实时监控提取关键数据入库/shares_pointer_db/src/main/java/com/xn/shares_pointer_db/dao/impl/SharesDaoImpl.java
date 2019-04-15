package com.xn.shares_pointer_db.dao.impl;

import com.xn.shares_pointer_db.dao.SharesDao;
import com.xn.shares_pointer_db.domain.Shares;
import com.xn.shares_pointer_db.utils.JDBCUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class SharesDaoImpl implements SharesDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public void insertAll(List<Shares> shares) {
        //1.定义sql
        String sql = "insert into shares(difficulty,time,height,nonce,workerId) values(?,?,?,?,?)";
        //2.执行sql
        //template.update(sql, shares.getDifficulty(),shares.getTime(),shares.getHeight(),shares.getNonce(),shares.getWorkerId());

        //批量入库方法
        template.batchUpdate(sql, new BatchPreparedStatementSetter() {

            public void setValues(PreparedStatement ps, int i) throws SQLException {

                    Integer difficulty = shares.get(i).getDifficulty();
                    String time = shares.get(i).getTime();
                    String height = shares.get(i).getHeight();
                    String nonce = shares.get(i).getNonce();
                    Integer workerId = shares.get(i).getWorkerId();
                    ps.setInt(1, difficulty);
                    ps.setString(2, time);
                    ps.setString(3, height);
                    ps.setString(4, nonce);
                    ps.setInt(5, workerId);
            }

            public int getBatchSize() {
                return shares.size();
            }

        });
    }

    @Override
    public void insert(Shares shares) {
        //1.定义sql
        String sql = "insert into shares(difficulty,time,height,nonce,workerId) values(?,?,?,?,?)";
        //2.执行sql
        template.update(sql, shares.getDifficulty(), shares.getTime(), shares.getHeight(), shares.getNonce(), shares.getWorkerId());
    }
}
