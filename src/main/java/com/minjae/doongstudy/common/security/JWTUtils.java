package com.minjae.doongstudy.common.security;

import com.minjae.doongstudy.domain.member.entity.Member;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JWTUtils {
    private SecretKey secretKey;
    public JWTUtils() {
        secretKey = Jwts.SIG.HS256.key().build();
    }
    public Claims getClaims(String token) {
        JwtParser parser = Jwts.parser().verifyWith(secretKey).build();
        var jws = parser.parseSignedClaims(token);
        return jws.getPayload();
    }
    public String createAccessToken(Member member) {
        return createToken("accessToken", member, 10);
    }
    public String createRefreshToken(Member member) {
        return createToken("refreshToken", member, 20);
    }
    private String createToken(String subject, Member member, long duration) {
        Date expiration = new Date(System.currentTimeMillis() + 1000*60*duration);
        return Jwts.builder()
                .subject(subject)
                .expiration(expiration)
                .claims(getClaims(member))
                .signWith(secretKey)
                .compact();
    }
    public Map<String, Object> getClaims(Member member) {
        return Map.of(
                "memberId", member.getMemberId(),
                "name", member.getName(),
                "email", member.getEmail()
        );
    }
}
