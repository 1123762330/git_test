package com.xn.shares_pointer_log2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Service
public class IRedisService<T> {
    @Autowired
    protected RedisTemplate<String, Object> redisTemplate;
    @Resource
    protected HashOperations<String, String, T> hashOperations;

    private static final String REDIS_KEY = "Shares_Key";

    public String getRedisKey() {
        return this.REDIS_KEY;
    }

    /**
     * 存入redis中的key
     *
     * @return
     */
    //protected abstract String getRedisKey();

    /**
     * 添加
     *
     * @param key    key
     * @param t 对象
     * @param expire 过期时间(单位:秒),传入 -1 时表示不设置过期时间
     */
    public void put(String key, T t, long expire) {
        hashOperations.put(getRedisKey(), key, t);
        if (expire != -1) {
            redisTemplate.expire(getRedisKey(), expire, TimeUnit.SECONDS);
        }
    }

    public void hput(String h,String key,T t){
        redisTemplate.opsForHash().put(h, key, t);
    }

    public T hget(String h,String key) {
        //get方法，根据key和hashkey找出对应的值
        return (T) redisTemplate.opsForHash().get(h, key);
    }


    /**
     * 删除
     *
     * @param key 传入key的名称
     */
    public void remove(String key) {
        hashOperations.delete(getRedisKey(), key);
    }

    /**
     * 查询
     *
     * @param key 查询的key
     * @return
     */
    public T get(String key) {
        return hashOperations.get(getRedisKey(), key);
    }

    /**
     * 获取当前redis库下所有对象
     *
     * @return
     */
    public List<T> getAll() {
        return hashOperations.values(getRedisKey());
    }

    /**
     * 查询查询当前redis库下所有key
     *
     * @return
     */
    public Set<String> getKeys() {
        return hashOperations.keys(getRedisKey());
    }

    /**
     * 判断key是否存在redis中
     *
     * @param key 传入key的名称
     * @return
     */
    public boolean isKeyExists(String key) {
        return hashOperations.hasKey(getRedisKey(), key);
    }

    public boolean isHKeyExists(String H,String key) {
        return hashOperations.hasKey(H, key);
    }

    /**
     * 查询当前key下缓存数量
     *
     * @return
     */
    public long count() {
        return hashOperations.size(getRedisKey());
    }

    /**
     * 清空redis
     */
    public void empty() {
        Set<String> set = hashOperations.keys(getRedisKey());
        set.stream().forEach(key -> hashOperations.delete(getRedisKey(), key));
    }

}