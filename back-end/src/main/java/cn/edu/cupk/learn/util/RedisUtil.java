package cn.edu.cupk.learn.util;

import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis缓存数据库api
 * @author Icy
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@AllArgsConstructor
public class RedisUtil {

    private RedisTemplate<Object, Object> redisTemplate;

    /**
     * 设置键值对
     * @param key 键
     * @param value 值
     */
    public void set(Object key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 设置键值对
     * @param key 键
     * @param value 值
     * @param second 过期时间（秒）
     */
    public void set(Object key, Object value, long second) {
        redisTemplate.opsForValue().set(key, value, second, TimeUnit.SECONDS);
    }

    /**
     * 设置键值对
     * @param key 键
     * @param value 值
     * @param time 过期时间
     * @param timeUnit 时间单位
     */
    public void set(Object key, Object value, long time, TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, time, timeUnit);
    }

    public void delete(Object key) {
        redisTemplate.delete(key);
    }

    /**
     * 根据键查找值
     * @param key 键
     * @return 值
     */
    @Nullable
    public Object get(Object key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 查找剩余时间
     * @param key 键
     * @return 剩余时间（秒）
     */
    @Nullable
    public Long getExpire(Object key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 查找剩余时间
     * @param key 键
     * @param timeUnit 时间单位
     * @return 剩余时间
     */
    @Nullable
    public Long getExpire(Object key, TimeUnit timeUnit) {
        return redisTemplate.getExpire(key, timeUnit);
    }

    /**
     * 创建单键值对map
     * @param key 键
     * @param value 值
     * @return map
     */
    public Map<String, Object> createMap(String key, Object value) {
        Map<String, Object> map = new HashMap<>();
        map.put(key, value);
        return map;
    }
}
