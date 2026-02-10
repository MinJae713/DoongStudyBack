package com.minjae.doongstudy.domain.thing.dto.response;

import com.minjae.doongstudy.domain.thing.entity.Thing;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetThingResponse {
    private Long thingId;
    private String name;
    private String description;
    private Integer price;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Long memberId;

    public static GetThingResponse from(Thing thing) {
        Long thingId = thing.getThingId();
        String name = thing.getName();
        String description = thing.getDescription();
        Integer price = thing.getPrice();
        LocalDate createdAt = thing.getCreatedAt();
        LocalDate updatedAt = thing.getUpdatedAt();
        Long memberId = thing.getMember().getMemberId();

        return GetThingResponse.builder()
                .thingId(thingId)
                .name(name)
                .description(description)
                .price(price)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .memberId(memberId)
                .build();
    }
}
