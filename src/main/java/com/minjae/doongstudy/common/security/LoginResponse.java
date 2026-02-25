package com.minjae.doongstudy.common.security;

import com.minjae.doongstudy.domain.member.entity.Member;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponse {
    private String accessToken;
    private Long memberId;
    private String name;
    private String email;
    public static LoginResponse from(Member member, String accessToken) {
        return LoginResponse.builder()
                .accessToken(accessToken)
                .memberId(member.getMemberId())
                .name(member.getName())
                .email(member.getEmail())
                .build();
    }
}
