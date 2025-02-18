package com.example.short_link.module.ShortLink.scheduler;


import com.example.short_link.module.ShortLink.repository.ShortLinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class CountAccessUpdater {
    private final RedisTemplate redisTemplate;
    private final ShortLinkRepository shortLinkRepository;

    @Scheduled(fixedRate = 10000)
    public void updateCountAccess() {
        Set<String> keys = redisTemplate.keys("CountAccessShortLink=*");
        if (keys == null || keys.isEmpty()) return;

        for (String key : keys) {
            String shortCode = key.replace("CountAccessShortLink=", "");

            String countStr = (String) redisTemplate.opsForValue().get(key);
            if (countStr == null) continue;

            Integer count = Integer.parseInt(countStr);

            System.out.println("Updating DB for " + shortCode + " with count: " + count);

            // Cập nhật vào DB
            shortLinkRepository.updateCountAccess(shortCode, count);

            // Xóa count trong Redis sau khi cập nhật DB
            redisTemplate.delete(key);
        }
    }

}
