package com.minjae.doongstudy.common.security.service;

import com.minjae.doongstudy.common.security.exception.InvalidRefreshTokenException;
import com.minjae.doongstudy.common.util.jwt.JWTUtils;
import com.minjae.doongstudy.common.util.redis.RedisService;
import com.minjae.doongstudy.domain.member.entity.Member;
import com.minjae.doongstudy.domain.member.exception.NoMemberException;
import com.minjae.doongstudy.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class MemberLogoutHandler implements LogoutHandler {
    private final JWTUtils jwtUtils;
    private final RedisService redisService;
    private final MemberRepository memberRepository;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       @Nullable Authentication authentication) {
        Map<String, Object> bodyMap = new HashMap<>();
        processRefreshToken(request, bodyMap);
        processAccessToken(request, bodyMap);
        String body = objectMapper.writeValueAsString(bodyMap);

        try {
            response.setStatus(HttpStatus.OK.value());
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json");
            System.out.println(body);
            response.getWriter().write(body);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processRefreshToken(HttpServletRequest request,
                                     Map<String, Object> bodyMap) {
        // Redis에서 삭제
        String refreshToken = request.getHeader("Refresh-Token");
        if (refreshToken == null || refreshToken.isEmpty())
            throw new InvalidRefreshTokenException();
        Claims claims = null;
        try {
            claims = jwtUtils.getClaims(refreshToken);
        } catch (ExpiredJwtException e) {
            bodyMap.put("refreshDeleted", false);
            bodyMap.put("refreshMessage", "RT가 이미 만료되었습니다");
            return; // 정상 처리 -> 만료되면 Redis 갈 필요 없음
        }
        // 여기서 만료되었다고 나오면? -> Redis 쪽에서도 token이 지워졌을 것
        // 그냥 끝내면 됨
        String email = claims.get("email", String.class);
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoMemberException::new);
        // Global Exception Handler가 없어서.. 좀 애매하네?
        Long memberId = member.getMemberId();
        boolean refreshDeleted = redisService.delete("refresh:"+memberId);
        String message = refreshDeleted ?
                "RT가 삭제되었습니다!" :
                "지워지진 않았습니다..! 이미 없어서 그럴거에유";
        bodyMap.put("refreshDeleted", refreshDeleted);
        bodyMap.put("refreshMessage", message);
    }
    private void processAccessToken(HttpServletRequest request,
                                    Map<String, Object> bodyMap) {
        // Black List 등록
        String accessToken = request.getHeader("Authorization");
        accessToken = extractAccessToken(accessToken);
        if (accessToken == null) {
            bodyMap.put("blacklistAdded", false);
            bodyMap.put("accessMessage", "이미 만료되었답니다");
            return;
        }
        Claims claims = null;
        try {
            claims = jwtUtils.getClaims(accessToken);
        } catch (ExpiredJwtException e) {
            bodyMap.put("blacklistAdded", false);
            bodyMap.put("accessMessage", "AT가 이미 만료되었습니다");
            return; // 정상 처리 -> 만료되면 Redis 갈 필요 없음
        }
        Date expireDate = claims.getExpiration();
        long expireTime = expireDate.getTime();
        long now = System.currentTimeMillis();
        Long ttl = expireTime-now; // milli second임!!! second로 넣지 마라
        redisService.saveBlacklist(accessToken, ttl);
        bodyMap.put("blacklistAdded", true);
        bodyMap.put("accessMessage", "AT가 Blacklist에 추가되었습니다");
    }
    private String extractAccessToken(String accessToken) {
        return accessToken == null || !accessToken.startsWith("Bearer ") ?
                null : accessToken.substring(7);
    }
}
