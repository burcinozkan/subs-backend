package com.subsTracker.subs.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@Slf4j
public class RedisControllerTest {

    private final StringRedisTemplate redisTemplate;
    private final RedisTemplate<String, Object> objectRedisTemplate;

    @GetMapping("/cache-test")
    public String cacheTest() {
        redisTemplate.opsForValue().set("testKey", "Hello Redis!");
        String value = redisTemplate.opsForValue().get("testKey");
        return "Redis bağlantısı başarılı! Değer: " + value;
    }

    @GetMapping("/cache-status")
    public Map<String, Object> getCacheStatus() {
        Map<String, Object> status = new HashMap<>();
        
        try {
            // Redis bağlantı durumu
            String pong = redisTemplate.getConnectionFactory().getConnection().ping();
            status.put("redis_connection", "OK - " + pong);
            
            // Cache keys
            Set<String> keys = redisTemplate.keys("subscription::*");
            status.put("cache_keys_count", keys != null ? keys.size() : 0);
            status.put("cache_keys", keys);
            
            // Memory usage
            Properties memoryInfo = redisTemplate.getConnectionFactory()
                .getConnection().info("memory");
            status.put("memory_info", memoryInfo);
            
            log.info("📊 Cache durumu kontrol edildi - {} cache key bulundu", keys != null ? keys.size() : 0);
            
        } catch (Exception e) {
            status.put("error", "Redis bağlantı hatası: " + e.getMessage());
            log.error("Redis durum kontrolü hatası: ", e);
        }
        
        return status;
    }

    @GetMapping("/cache-clear")
    public String clearCache() {
        try {
            Set<String> keys = redisTemplate.keys("subscription::*");
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.info("🗑️ Cache temizlendi - {} key silindi", keys.size());
                return "Cache temizlendi! " + keys.size() + " key silindi.";
            } else {
                return "Cache zaten boş!";
            }
        } catch (Exception e) {
            log.error("Cache temizleme hatası: ", e);
            return "Cache temizleme hatası: " + e.getMessage();
        }
    }
}
