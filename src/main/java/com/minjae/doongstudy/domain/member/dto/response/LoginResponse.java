package com.minjae.doongstudy.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginResponse {
    private Boolean apiResult;
    private String message;
    private Long memberId;
    private String name;
}
