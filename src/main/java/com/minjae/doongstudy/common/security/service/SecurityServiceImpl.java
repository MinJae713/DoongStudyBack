package com.minjae.doongstudy.common.security.service;

import com.minjae.doongstudy.common.security.dto.response.RefreshResponse;
import com.minjae.doongstudy.common.security.exception.InvalidRefreshTokenException;
import com.minjae.doongstudy.common.util.jwt.JWTUtils;
import com.minjae.doongstudy.common.util.redis.RedisService;
import com.minjae.doongstudy.domain.member.entity.Member;
import com.minjae.doongstudy.domain.member.exception.NoMemberException;
import com.minjae.doongstudy.domain.member.repository.MemberRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SecurityServiceImpl implements SecurityService{
    private final MemberRepository memberRepository;
    private final JWTUtils jwtUtils;
    private final RedisService redisService;

    @Value("${service.refresh-token-duration}")
    private Long refreshTokenDuration;

    @Override
    @Transactional
    public RefreshResponse refresh(String refreshToken) {
        if (refreshToken == null || refreshToken.isEmpty())
            throw new InvalidRefreshTokenException();
        Claims claims = jwtUtils.getClaims(refreshToken); // token 만료 예외 ㄱㄴ
        String email = claims.get("email").toString();
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(NoMemberException::new);
        String saved = redisService.get("refresh:"+member.getMemberId());
        if (!refreshToken.equals(saved))
            throw new InvalidRefreshTokenException();
        String accessToken = jwtUtils.createAccessToken(member);
        refreshToken = jwtUtils.createRefreshToken(member);
        redisService.saveRefreshToken(member.getMemberId(), refreshToken, refreshTokenDuration);
        return RefreshResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
