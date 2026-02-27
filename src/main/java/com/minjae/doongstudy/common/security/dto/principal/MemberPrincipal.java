package com.minjae.doongstudy.common.security.dto.principal;

import io.jsonwebtoken.Claims;
import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberPrincipal {
    private Long memberId;
    private String name;
    private String email;
    public static MemberPrincipal fromClaims(Claims claims) {
        Long memberId = claims.get("memberId", Long.class);
        String name = claims.get("name", String.class);
        String email = claims.get("email", String.class);

        return MemberPrincipal.builder()
                .memberId(memberId)
                .name(name)
                .email(email)
                .build();
    }
}
