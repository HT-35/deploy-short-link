package com.example.short_link.shared.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        // Tạo một instance của RedisTemplate để thao tác với Redis
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        // Thiết lập factory kết nối Redis, giúp template có thể kết nối với Redis server
        template.setConnectionFactory(redisConnectionFactory);
        // Cấu hình serializer cho key dưới dạng String để dễ đọc và ghi
        template.setKeySerializer(new StringRedisSerializer());
        // Cấu hình serializer cho value cũng dưới dạng String thay vì dạng byte array mặc định
        template.setValueSerializer(new StringRedisSerializer());
        // Trả về template đã được cấu hình, để Spring quản lý
        return template;
    }
}