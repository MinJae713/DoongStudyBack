package com.minjae.doongstudy.common.security.exception;

public class InvalidRefreshTokenException extends SecurityException {
    public InvalidRefreshTokenException() {
        super("Refresh Token이 유효하지 않습니다");
    }
}
