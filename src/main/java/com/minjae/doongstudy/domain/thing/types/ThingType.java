package com.minjae.doongstudy.domain.thing.types;

import lombok.Getter;

@Getter
public enum ThingType {
    INSTRUMENT("악기"),
    BOOK("도서"),
    COMPUTER("컴퓨터"),
    ETC("기타");
    private final String label;
    private ThingType(String label) {
        this.label = label;
    }
}
