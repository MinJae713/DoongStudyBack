package com.minjae.doongstudy.common.security.filter;

import com.minjae.doongstudy.common.security.types.SecurityExceptionType;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
public class SecurityExceptionFilter extends OncePerRequestFilter {
    private ObjectMapper objectMapper;
    public SecurityExceptionFilter() {
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            createResponseBody(
                    e.getMessage(), SecurityExceptionType.ACCESS_TOKEN_EXPIRATION,
                    HttpStatus.UNAUTHORIZED, response);
        } catch (SecurityException e) {
            createResponseBody(e.getMessage(), SecurityExceptionType.ETC,
                    HttpStatus.UNAUTHORIZED, response);
        } catch (RuntimeException e) {
            // 사용자 없을 때 여기서 잡음
            createResponseBody(e.getMessage(), SecurityExceptionType.ETC,
                    HttpStatus.BAD_REQUEST, response);
        }
    }

    private void createResponseBody(String message, SecurityExceptionType type,
                                    HttpStatus httpStatus, HttpServletResponse response) throws IOException{
        Map<String,Object> bodyMap = Map.of(
                "message", message,
                "type", type
        );
        String body = objectMapper.writeValueAsString(bodyMap);
        response.setStatus(httpStatus.value());
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(body);
    }
}
