package com.example.short_link.module.OriginalLink.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    // lưu trữ bản ghi
    public void saveKey(String uuid, int count) {
        redisTemplate.opsForValue().set(uuid, String.valueOf(count), 24, TimeUnit.HOURS);
    }

    // lấy số lần tạo bản ghi
    public Integer getKeyCount(String uuid) {
        String count = (String) redisTemplate.opsForValue().get(uuid);
        return (count != null) ? Integer.parseInt(count) : null;
    }


    // count  create link limit   5 or 3
    public void incrementKey(String uuid) {
        redisTemplate.opsForValue().increment(uuid);
    }

    // ============================================================================

    //    get Original Link by Short Link
    public String getLinkOriginalbyShortLink(String shortLink) {

        String linkOriginal = (String) redisTemplate.opsForValue().get(shortLink);

        return linkOriginal != null ? linkOriginal : null;
    }


    // save originlink
    public void saveKeyString(String key, String value) {
        this.redisTemplate.opsForValue().set(key, value, 24, TimeUnit.HOURS);
    }


    //  Get  count_access original Link
    public Integer getcountAccessOriginalLink(String key) {
        String countAccess = (String) this.redisTemplate.opsForValue().get(key);
        return (countAccess != null) ? Integer.parseInt(countAccess) : null;
    }

    // count_access original link
    public void updateCountAccessShortLink(String accessKey) {
        this.redisTemplate.opsForValue().increment(accessKey);
    }


    public void removekey(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            throw new IllegalArgumentException("UUID không được null hoặc rỗng");
        }
        redisTemplate.delete(uuid);
    }
}
