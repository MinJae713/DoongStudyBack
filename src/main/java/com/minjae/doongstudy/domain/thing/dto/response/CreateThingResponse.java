package com.minjae.doongstudy.domain.thing.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateThingResponse {
    private Boolean apiResult;
    private Long thingId;
}
