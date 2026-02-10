package com.minjae.doongstudy.domain.member.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RegisterResponse {
    private Boolean apiResult;
    private Long memberId;
}
