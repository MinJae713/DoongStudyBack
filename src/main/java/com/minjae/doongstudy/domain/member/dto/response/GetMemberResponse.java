package com.minjae.doongstudy.domain.member.dto.response;

import com.minjae.doongstudy.domain.member.entity.Member;
import com.minjae.doongstudy.domain.member.types.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetMemberResponse {
    private Long memberId;
    private String email;
    private String name;
    private Integer age;
    private Gender gender;
    private String address;
    private LocalDate createdAt;
    private LocalDate updatedAt;

    public static GetMemberResponse from(Member member) {
        Long memberId = member.getMemberId();
        String email = member.getEmail();
        String name = member.getName();
        Integer age = member.getAge();
        Gender gender = member.getGender();
        String address = member.getAddress();
        LocalDate createdAt = member.getCreatedAt();
        LocalDate updatedAt = member.getUpdatedAt();

        return GetMemberResponse.builder()
                .memberId(memberId)
                .email(email)
                .name(name)
                .age(age)
                .gender(gender)
                .address(address)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();
    }
}
