package com.minjae.doongstudy.common.security.controller;

import com.minjae.doongstudy.common.security.dto.response.RefreshResponse;
import com.minjae.doongstudy.common.security.service.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class SecurityController {
    private final SecurityService securityService;

    @PostMapping("/refresh")
    @Transactional
    public ResponseEntity<RefreshResponse> refresh(
            @RequestHeader("Refresh-Token") String refreshToken) {
        return ResponseEntity.ok(securityService.refresh(refreshToken));
    }
}
