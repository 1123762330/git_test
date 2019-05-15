package com.spring.xnpool.bmpos.tasks.bmpostasks.mapper;

import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.ConfigMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.PluginsStateMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.dao.mapper.SharesMapper;
import com.spring.xnpool.bmpos.tasks.bmpostasks.service.IErrorService;
import com.spring.xnpool.bmpos.tasks.bmpostasks.taskSettlement.TaskPayout;
import com.spring.xnpool.bmpos.tasks.bmpostasks.tools.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConnTestCase {
    @Autowired
    DataSource dataSource;

    @Autowired
    SharesMapper sharesMapper;
    @Autowired
    IErrorService errorService;
    @Autowired
    TaskPayout taskPayout;
    @Autowired
    ConfigMapper configMapper;
    @Autowired
    PluginsStateMapper pluginsStateMapper;
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    StringRedisTemplate stringRedisTemplate;
    @Autowired
    RedisUtil redisUtil;

    @Test
    public void conn() throws SQLException {
       List<String> usernameAndUser = sharesMapper.selectusernameByDay(6);
       System.err.println(usernameAndUser);
    }


}
