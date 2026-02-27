package com.minjae.doongstudy.common.security.filter;

import com.minjae.doongstudy.common.util.jwt.JWTUtils;
import com.minjae.doongstudy.common.security.dto.response.LoginResponse;
import com.minjae.doongstudy.common.security.dto.userDetails.MemberDetails;
import com.minjae.doongstudy.common.util.redis.RedisService;
import com.minjae.doongstudy.domain.member.entity.Member;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private ObjectMapper objectMapper;
    private JWTUtils jwtUtils;
    private RedisService redisService;

    @Value("${service.refresh-token-duration}")
    private Long refreshTokenDuration;

    public JwtAuthenticationFilter(JWTUtils jwtUtils,
                                   AuthenticationManager authenticationManager,
                                   RedisService redisService) {
        super(authenticationManager);
        this.objectMapper = new ObjectMapper();
        this.jwtUtils = jwtUtils;
        this.redisService = redisService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                        HttpServletResponse response) throws AuthenticationException {
        try {
            Map<String, String> body = objectMapper.readValue(request.getInputStream(), Map.class);
            String email = body.get("email");
            String password = body.get("password");
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(email, password);
            return getAuthenticationManager().authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                        FilterChain chain, Authentication authResult) throws IOException, ServletException {
        MemberDetails memberDetails = (MemberDetails)authResult.getPrincipal();
        Member member = memberDetails.getMember();
        String accessToken = jwtUtils.createAccessToken(member);
        String refreshToken = jwtUtils.createRefreshToken(member);

        redisService.saveRefreshToken(member.getMemberId(), refreshToken, refreshTokenDuration);

        LoginResponse loginResponse = LoginResponse.from(member, accessToken, refreshToken);
        String responseBody = objectMapper.writeValueAsString(loginResponse);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter().write(responseBody);
    }

}
