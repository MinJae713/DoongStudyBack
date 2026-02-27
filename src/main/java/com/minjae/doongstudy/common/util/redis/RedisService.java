package com.minjae.doongstudy.common.util.redis;

public interface RedisService {
    void save(String key, String value, Long ttl);
    void saveBlacklist(String accessToken, Long ttl);
    String get(String key);
    boolean hasKey(String key);
    boolean delete(String key);
    void setBlackList(Long memberId, String accessToken, Long ttl);
    void saveRefreshToken(Long memberId, String refreshToken, Long ttl);
}
