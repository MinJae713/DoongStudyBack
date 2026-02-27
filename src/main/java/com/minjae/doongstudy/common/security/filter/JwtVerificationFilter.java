package com.minjae.doongstudy.common.security.filter;

import com.minjae.doongstudy.common.security.dto.principal.MemberPrincipal;
import com.minjae.doongstudy.common.util.jwt.JWTUtils;
import com.minjae.doongstudy.common.util.redis.RedisService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtVerificationFilter extends OncePerRequestFilter {
    private final JWTUtils jwtUtils;
//    private final MemberDetailsService memberDetailsService;
    private final RedisService redisService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = extractToken(request);
        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Black List 도입 -> Redis에 blacklist 등록된 token이라면 인증 못해유!
        String blackListValue = redisService.get("blacklist:"+token);
        if (blackListValue != null)
            throw new SecurityException("로그아웃된 사용자입니다. 다시 로그인해주세요");

        Claims claims = jwtUtils.getClaims(token);
//        String email = claims.get("email").toString();
//        UserDetails userDetails = memberDetailsService.loadUserByUsername(email);
        // 세션 방식이면 이거슬 쓴다고 합니다 -> 근데 JWT를 쓰니까 DB를 조회할 필요 없다!
        MemberPrincipal memberPrincipal = MemberPrincipal.fromClaims(claims);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(memberPrincipal, null, List.of());
        // 지금은 권한 개념이 없는데, 정석으로 할라면 AT에 권한도 넣어줘야 된다잉?
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        return token != null && token.startsWith("Bearer ") ?
                token.substring(7) : null;
    }
}
