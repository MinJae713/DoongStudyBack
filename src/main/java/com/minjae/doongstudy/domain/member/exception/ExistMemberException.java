package com.minjae.doongstudy.domain.member.exception;

public class ExistMemberException extends RuntimeException {
    public ExistMemberException() {
        super("해당 멤버가 이미 존재합니다");
    }
}
