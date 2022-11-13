package com.xs.service;

public interface RedisService {

    <T> T getObjectByValue(String key, Class t);

    void saveObjectToValue(String key, Object object);
}
