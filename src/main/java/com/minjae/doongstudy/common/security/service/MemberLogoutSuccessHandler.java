package com.minjae.doongstudy.common.security.service;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

@Component
public class MemberLogoutSuccessHandler implements LogoutSuccessHandler {
    private ObjectMapper objectMapper;

    public MemberLogoutSuccessHandler() {
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response,
                                @Nullable Authentication authentication) throws IOException, ServletException {
        Map<String, Object> bodyMap = (Map) request.getAttribute("body");
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
}
