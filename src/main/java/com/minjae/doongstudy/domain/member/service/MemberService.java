package com.minjae.doongstudy.domain.member.service;

import com.minjae.doongstudy.domain.member.dto.request.RegisterRequest;
import com.minjae.doongstudy.domain.member.dto.response.DeleteMemberResponse;
import com.minjae.doongstudy.domain.member.dto.response.GetMemberResponse;
import com.minjae.doongstudy.domain.member.dto.response.RegisterResponse;

import java.util.List;

public interface MemberService {
//    LoginResponse login(LoginRequest loginRequest);
    RegisterResponse register(RegisterRequest registerRequest);
    List<GetMemberResponse> getMembers();
    GetMemberResponse getMemberById(Long memberId);
    DeleteMemberResponse deleteMember(Long memberId);
}
