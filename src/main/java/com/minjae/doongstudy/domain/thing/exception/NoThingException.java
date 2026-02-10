package com.minjae.doongstudy.domain.thing.exception;

public class NoThingException extends RuntimeException {
    public NoThingException() {
        super("해당 물건이 없습니다");
    }
}
