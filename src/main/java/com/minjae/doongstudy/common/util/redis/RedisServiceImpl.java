package com.minjae.doongstudy.common.util.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisServiceImpl implements RedisService{
    private final StringRedisTemplate redisTemplate;

    @Override
    public void save(String key, String value, Long ttl) {
        redisTemplate.opsForValue().set(key, value, ttl, TimeUnit.SECONDS);
    }

    @Override
    public void saveBlacklist(String accessToken, Long ttl) {
        redisTemplate.opsForValue().set("blacklist:"+accessToken, ttl.toString(), ttl, TimeUnit.MILLISECONDS);
    }

    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean delete(String key) {
        return redisTemplate.delete(key);
    }

    @Override
    public void saveRefreshToken(Long memberId, String refreshToken, Long ttl) {
        save("refresh:"+memberId, refreshToken, ttl);
    }
}
