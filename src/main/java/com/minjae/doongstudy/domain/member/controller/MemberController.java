package com.minjae.doongstudy.domain.member.controller;

import com.minjae.doongstudy.common.security.dto.principal.MemberPrincipal;
import com.minjae.doongstudy.domain.member.dto.request.RegisterRequest;
import com.minjae.doongstudy.domain.member.dto.response.DeleteMemberResponse;
import com.minjae.doongstudy.domain.member.dto.response.GetMemberResponse;
import com.minjae.doongstudy.domain.member.dto.response.RegisterResponse;
import com.minjae.doongstudy.domain.member.service.MemberService;
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

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(
//            @Valid @RequestBody LoginRequest loginRequest) {
//        // 얘 이제 필요 없음 -> Security Filter Chain에서 끝남
//        return ResponseEntity.ok(memberService.login(loginRequest));
//    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @Valid @RequestBody RegisterRequest registerRequest) {
        return ResponseEntity.ok(memberService.register(registerRequest));
    }

    @GetMapping
    public ResponseEntity<List<GetMemberResponse>> getMembers(
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        return ResponseEntity.ok(memberService.getMembers());
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<GetMemberResponse> getMemberById(
            @PathVariable Long memberId,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        return ResponseEntity.ok(memberService.getMemberById(memberId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<DeleteMemberResponse> deleteMember(
            @PathVariable Long memberId,
            @AuthenticationPrincipal MemberPrincipal memberPrincipal) {
        return ResponseEntity.ok(memberService.deleteMember(memberId));
    }
}
