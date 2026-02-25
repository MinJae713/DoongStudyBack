package com.minjae.doongstudy.common.config;

import com.minjae.doongstudy.common.security.JWTUtils;
import com.minjae.doongstudy.common.security.JwtAuthenticationFilter;
import com.minjae.doongstudy.common.security.JwtVerificationFilter;
import com.minjae.doongstudy.common.security.SecurityExceptionFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           JwtAuthenticationFilter authenticationFilter,
                                           JwtVerificationFilter verificationFilter,
                                           SecurityExceptionFilter securityExceptionFilter) throws Exception {
        authenticationFilter.setFilterProcessesUrl("/api/member/login");

        http.formLogin(AbstractHttpConfigurer::disable)
            .addFilterAt(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(verificationFilter, JwtAuthenticationFilter.class)
            .addFilterBefore(securityExceptionFilter, JwtVerificationFilter.class)
            .cors(Customizer.withDefaults())
            .csrf(CsrfConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/member/login").permitAll()
                    .requestMatchers("/api/member/register").permitAll()
                    .anyRequest().authenticated() // 🔥 처음엔 전부 허용 추천
            );

        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        // 프론트엔드 주소 허용
        configuration.setAllowedOriginPatterns(Collections.singletonList("*"));

        // 허용할 HTTP 메서드
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));

        // 허용할 헤더
        configuration.setAllowedHeaders(Collections.singletonList("*"));

        // 쿠키 및 인증 정보 허용 (필수)
        configuration.setAllowCredentials(true);

        // 프론트엔드에서 읽을 수 있는 헤더 노출 (토큰 관련)
        configuration.setExposedHeaders(Arrays.asList("Authorization", "Set-Cookie"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 경로에 대해 위 설정 적용
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
