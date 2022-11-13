package com.xs.service.impl;

import com.xs.service.RedisService;
import com.xs.util.JacksonUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisTemplate jsonRedisTemplate;

    @Override
    public <T> T getObjectByValue(String key, Class t) {
        Object redisResult = jsonRedisTemplate.opsForValue().get(key);
        return (T) JacksonUtils.convertValue(redisResult, t);
    }

    @Override
    public void saveObjectToValue(String key, Object object) {
        jsonRedisTemplate.opsForValue().set(key, object);
    }
}
