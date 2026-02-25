package com.minjae.doongstudy.domain.member.controller;

import com.minjae.doongstudy.common.security.MemberDetails;
import com.minjae.doongstudy.domain.member.dto.request.LoginRequest;
import com.minjae.doongstudy.domain.member.dto.request.RegisterRequest;
import com.minjae.doongstudy.domain.member.dto.response.DeleteMemberResponse;
import com.minjae.doongstudy.domain.member.dto.response.GetMemberResponse;
import com.minjae.doongstudy.domain.member.dto.response.LoginResponse;
import com.minjae.doongstudy.domain.member.dto.response.RegisterResponse;
import com.minjae.doongstudy.domain.member.entity.Member;
import com.minjae.doongstudy.domain.member.service.MemberService;
import com.minjae.doongstudy.domain.thing.dto.response.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest loginRequest) {
        // 얘 이제 필요 없음 -> Security Filter Chain에서 끝남
        return ResponseEntity.ok(memberService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(memberService.register(registerRequest));
    }

    @GetMapping
    public ResponseEntity<List<GetMemberResponse>> getMembers(
            @AuthenticationPrincipal MemberDetails memberDetails) {
        Member member = memberDetails.getMember();
        System.out.println(member.getName()+", "+member.getEmail());
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<GetMemberResponse> getMemberById(
            @PathVariable Long memberId,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<DeleteMemberResponse> deleteMember(
            @PathVariable Long memberId,
            @AuthenticationPrincipal MemberDetails memberDetails) {
        return ResponseEntity.ok(memberService.deleteMember(memberId));
    }
}
