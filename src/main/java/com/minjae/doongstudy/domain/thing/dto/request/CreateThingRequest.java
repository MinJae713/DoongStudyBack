package com.minjae.doongstudy.domain.thing.dto.request;

import com.minjae.doongstudy.domain.thing.types.ThingType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateThingRequest {

    @NotNull(message = "이름은 필수값입니다")
    private String name;

    private String description;

    @NotNull(message = "가격은 필수값입니다")
    @Positive(message = "가격은 양수여야 합니다")
    private Integer price;

    @NotNull(message = "물건 유형은 필수값입니다")
    private ThingType type;

    @NotNull(message = "멤버 아이디는 필수값입니다")
    @Positive(message = "멤버 아이디는 양수여야 합니다")
    private Long memberId;
}
