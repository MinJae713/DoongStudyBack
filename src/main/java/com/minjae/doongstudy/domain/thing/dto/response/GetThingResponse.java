package com.minjae.doongstudy.domain.thing.dto.response;

import com.minjae.doongstudy.domain.member.entity.Member;
import com.minjae.doongstudy.domain.thing.entity.Thing;
import com.minjae.doongstudy.domain.thing.types.ThingType;
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
    private ThingType type;
    private LocalDate createdAt;
    private LocalDate updatedAt;
    private Long memberId;
    private String memberName;

    public static GetThingResponse from(Thing thing) {
        Long thingId = thing.getThingId();
        String name = thing.getName();
        String description = thing.getDescription();
        Integer price = thing.getPrice();
        ThingType type = thing.getType();
        LocalDate createdAt = thing.getCreatedAt();
        LocalDate updatedAt = thing.getUpdatedAt();
        Member member = thing.getMember();
        Long memberId = member.getMemberId();
        String memberName = member.getName();

        return GetThingResponse.builder()
                .thingId(thingId)
                .name(name)
                .description(description)
                .price(price)
                .type(type)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .memberId(memberId)
                .memberName(memberName)
                .build();
    }
}
