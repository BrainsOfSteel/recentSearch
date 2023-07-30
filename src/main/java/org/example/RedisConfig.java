package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;

@Configuration
public class RedisConfig {
    @Bean
    public CachedRedisObjectTemplate customObjectRedisTemplate(RedisConnectionFactory connectionFactory) {
        return new CachedRedisObjectTemplate(connectionFactory);
    }
}

