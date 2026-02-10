package com.minjae.doongstudy.domain.member.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginRequest {
    @NotNull(message = "이메일은 필수값입니다")
    private String email;

    @NotNull(message = "비밀번호는 필수값입니다")
    private String password;
}
