package com.minjae.doongstudy.domain.thing.service;

import com.minjae.doongstudy.domain.member.entity.Member;
import com.minjae.doongstudy.domain.member.exception.NoMemberException;
import com.minjae.doongstudy.domain.member.repository.MemberRepository;
import com.minjae.doongstudy.domain.thing.dto.request.CreateThingRequest;
import com.minjae.doongstudy.domain.thing.dto.request.UpdateThingRequest;
import com.minjae.doongstudy.domain.thing.dto.response.*;
import com.minjae.doongstudy.domain.thing.entity.Thing;
import com.minjae.doongstudy.domain.thing.exception.NoThingException;
import com.minjae.doongstudy.domain.thing.repository.ThingRepository;
import com.minjae.doongstudy.domain.thing.types.ThingType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ThingServiceImpl implements ThingService {
    private final MemberRepository memberRepository;
    private final ThingRepository thingRepository;

    @Override
    public List<GetThingResponse> getThings(Long memberId) {
        List<Thing> things = thingRepository.findAll();
        return things.stream().map(thing -> GetThingResponse.from(thing, memberId)).toList();
    }

    @Override
    public GetThingResponse getThingById(Long thingId, Long memberId) {
        Thing thing = thingRepository.findById(thingId)
                .orElseThrow(NoThingException::new);
        return GetThingResponse.from(thing, memberId);
    }

    @Override
    @Transactional
    public CreateThingResponse createThing(CreateThingRequest createThingRequest) {
        Long memberId = createThingRequest.getMemberId();
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NoMemberException::new);
        Thing thing = Thing.from(createThingRequest, member);
        Thing saved = thingRepository.save(thing);
        return CreateThingResponse.builder()
                .apiResult(true)
                .thingId(saved.getThingId())
                .build();
    }

    @Override
    @Transactional
    public UpdateThingResponse updateThing(UpdateThingRequest updateThingRequest) {
        Long thingId = updateThingRequest.getThingId();
        Thing thing = thingRepository.findById(thingId)
                .orElseThrow(NoThingException::new);

        String name = updateThingRequest.getName();
        if (name != null) thing.setName(name);
        String description = updateThingRequest.getDescription();
        if (description != null) thing.setDescription(description);
        Integer price = updateThingRequest.getPrice();
        if (price != null) thing.setPrice(price);
        ThingType type = updateThingRequest.getType();
        if (type != null) thing.setType(type);
        Long memberId = updateThingRequest.getMemberId();
        if (memberId != null) {
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(NoMemberException::new);
            thing.setMember(member);
        }
        Thing saved = thingRepository.save(thing);
        return UpdateThingResponse.builder()
                .apiResult(true)
                .thingId(saved.getThingId())
                .build();
    }

    @Override
    @Transactional
    public DeleteThingResponse deleteThing(Long thingId) {
        thingRepository.findById(thingId)
                .orElseThrow(NoThingException::new);
        thingRepository.deleteById(thingId);
        return DeleteThingResponse.builder()
                .apiResult(true)
                .build();
    }
}
