package org.example.service;

import org.example.CachedRedisObjectTemplate;
import org.example.model.CachedSearch;
import org.example.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedisService.class);

    private final CachedRedisObjectTemplate customObjectRedisTemplate;

    public RedisService(CachedRedisObjectTemplate customObjectRedisTemplate) {
        this.customObjectRedisTemplate = customObjectRedisTemplate;
    }

    public void pushListStrings(String key, List<CachedSearch> values){
        if(values == null || values.size() == 0){
            return;
        }
        ListOperations<String, CachedSearch> listOperations = customObjectRedisTemplate.opsForList();
        listOperations.rightPushAll(key, values);
        customObjectRedisTemplate.expire(key, Util.MINUTES_IN_DAY, TimeUnit.MINUTES);
    }

    //Not isolation safe -> but can user be concurrently update this??
    public void pushString(String key, CachedSearch value){
        ListOperations<String, CachedSearch> listOperations = customObjectRedisTemplate.opsForList();
        Long size = listOperations.size(key);
        if (size != null && size >= Util.REDIS_RESPONSE_SIZE) {
            listOperations.rightPop(key);
        }
        listOperations.leftPush(key, value);
        customObjectRedisTemplate.expire(key, Util.MINUTES_IN_DAY, TimeUnit.MINUTES);
    }

    public List<CachedSearch> getRecentSearches(String key){
        ListOperations<String, CachedSearch> listOperations = customObjectRedisTemplate.opsForList();
        return listOperations.range(key, 0, -1);
    }
}
