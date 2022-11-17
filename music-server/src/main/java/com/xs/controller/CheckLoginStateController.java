package com.xs.controller;

import com.xs.common.Result;
import com.xs.service.RedisService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class CheckLoginStateController {

    @Resource
    private RedisService redisService;

    @GetMapping("/checkLoginState/{username}")
    public Result checkLoginState(@PathVariable String username) {
        return redisService.checkLoginState(username);
    }
}
