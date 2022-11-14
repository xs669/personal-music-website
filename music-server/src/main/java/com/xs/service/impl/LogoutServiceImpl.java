package com.xs.service.impl;

import com.xs.common.Result;
import com.xs.service.LogoutService;
import com.xs.util.RedisCache;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LogoutServiceImpl implements LogoutService {

    @Resource
    private RedisCache redisCache;

    /**
     * 退出登录
     */
    @Override
    public Result logout() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = (String) authentication.getPrincipal();
        redisCache.deleteObject(username);
        return Result.ok("退出登录成功");
    }
}
