package com.minjae.doongstudy.domain.member.entity;

import com.minjae.doongstudy.domain.member.dto.request.RegisterRequest;
import com.minjae.doongstudy.domain.member.types.Gender;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private Integer age;

    @Column(nullable = false)
    private Gender gender;

    private String address;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    public static Member from(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        String name = registerRequest.getName();
        Integer age = registerRequest.getAge();
        Gender gender = registerRequest.getGender();
        String address = registerRequest.getAddress();

        return Member.builder()
                .email(email)
                .password(password)
                .name(name)
                .age(age)
                .gender(gender)
                .address(address)
                .build();
    }
}
