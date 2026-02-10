package com.minjae.doongstudy.domain.thing.entity;

import com.minjae.doongstudy.domain.member.entity.Member;
import com.minjae.doongstudy.domain.thing.dto.request.CreateThingRequest;
import com.minjae.doongstudy.domain.thing.types.ThingType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Thing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long thingId;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private Integer price;

    @CreationTimestamp
    private LocalDate createdAt;

    @UpdateTimestamp
    private LocalDate updatedAt;

    @Column(nullable = false)
    private ThingType type;

    @ManyToOne
    @JoinColumn(name = "memberId")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Member member;

    public static Thing from(CreateThingRequest createThingRequest, Member member) {
        String name = createThingRequest.getName();
        String description = createThingRequest.getDescription();
        Integer price = createThingRequest.getPrice();
        ThingType type = createThingRequest.getType();
        return Thing.builder()
                .name(name)
                .description(description)
                .price(price)
                .type(type)
                .member(member)
                .build();
    }
}
