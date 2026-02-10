package com.minjae.doongstudy.domain.member.dto.request;

import com.minjae.doongstudy.domain.member.types.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RegisterRequest {

    @NotNull(message = "이메일은 필수값입니다")
    @Email(message = "이메일 형식을 지켜주세요")
    private String email;

    @NotNull(message = "비밀번호는 필수값입니다")
    private String password;

    @NotNull(message = "이름은 필수값입니다")
    private String name;

    @Positive(message = "나이는 양수여야 합니다")
    private Integer age;

    @NotNull(message = "성별은 필수값입니다")
    private Gender gender;

    private String address;
}
