package org.example;

import org.example.model.CachedSearch;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

public class CachedRedisObjectTemplate extends RedisTemplate<String, CachedSearch> {
    public CachedRedisObjectTemplate(RedisConnectionFactory connectionFactory) {
        setConnectionFactory(connectionFactory);
        setKeySerializer(RedisSerializer.string());
        setValueSerializer(new CachedRedisObjectSerializer());
        setHashKeySerializer(RedisSerializer.string());
        setHashValueSerializer(new CachedRedisObjectSerializer());
        afterPropertiesSet();
    }
}
