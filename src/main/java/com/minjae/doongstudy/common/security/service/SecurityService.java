package com.minjae.doongstudy.common.security.service;

import com.minjae.doongstudy.common.security.dto.response.RefreshResponse;

public interface SecurityService {
    RefreshResponse refresh(String refreshToken);
}
