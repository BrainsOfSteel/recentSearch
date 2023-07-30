package org.example;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.model.CachedSearch;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.IOException;

public class CachedRedisObjectSerializer implements RedisSerializer<CachedSearch> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(CachedSearch customObject) throws SerializationException {
        try {
            return objectMapper.writeValueAsBytes(customObject);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error serializing CustomObject", e);
        }
    }

    @Override
    public CachedSearch deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0) {
            return null;
        }
        try {
            return objectMapper.readValue(bytes, CachedSearch.class);
        } catch (JsonProcessingException e) {
            throw new SerializationException("Error deserializing CustomObject", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
