package com.xs.service.impl;

import com.xs.common.Result;
import com.xs.domain.Admin;
import com.xs.service.RedisService;
import com.xs.util.RedisCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private RedisCache redisCache;

    @Override
    public Result checkLoginState(String username) {
        Admin admin = redisCache.getCacheObject(username);
        if (Objects.nonNull(admin)) {
            return Result.ok("token生效中", true);
        } else {
            return Result.ok("凭证已失效", false);
        }
    }
}
