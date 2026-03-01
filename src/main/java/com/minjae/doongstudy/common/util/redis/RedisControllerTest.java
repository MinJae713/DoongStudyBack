package com.minjae.doongstudy.common.util.redis;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/redisTest")
@RequiredArgsConstructor
public class RedisControllerTest {
    private final RedisService redisService;
    private String key = "testKey";

    @PostMapping
    public ResponseEntity<String> save() {
        redisService.save(key, "keykeykey", 60L);
        return ResponseEntity.ok("Save Success");
    }

    @GetMapping
    public ResponseEntity<String> get() {
        return ResponseEntity.ok(redisService.get(key));
    }

    @GetMapping("/hasKey")
    public ResponseEntity<String> hasKey() {
        return ResponseEntity.ok(String.format("%s -> %b", key, redisService.hasKey(key)));
    }

    @DeleteMapping
    public ResponseEntity<String> delete() {
        return ResponseEntity.ok(redisService.delete(key) ? "Delete Success" : "Delete Fail");
    }

    @PostMapping("/blackList")
    public ResponseEntity<String> setBlackList() {
        Long memberId = 713L;
        redisService.saveBlacklist("검은리스트", 60L);
        return ResponseEntity.ok("Set Black List Success");
    }
}
