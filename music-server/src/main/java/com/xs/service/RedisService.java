package com.xs.service;

import com.xs.common.Result;

public interface RedisService {

    Result checkLoginState(String username);
}
