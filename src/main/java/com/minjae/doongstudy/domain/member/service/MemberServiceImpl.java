package com.minjae.doongstudy.domain.member.service;

import com.minjae.doongstudy.domain.member.dto.request.LoginRequest;
import com.minjae.doongstudy.domain.member.dto.request.RegisterRequest;
import com.minjae.doongstudy.domain.member.dto.response.DeleteMemberResponse;
import com.minjae.doongstudy.domain.member.dto.response.GetMemberResponse;
import com.minjae.doongstudy.domain.member.dto.response.LoginResponse;
import com.minjae.doongstudy.domain.member.dto.response.RegisterResponse;
import com.minjae.doongstudy.domain.member.entity.Member;
import com.minjae.doongstudy.domain.member.exception.ExistMemberException;
import com.minjae.doongstudy.domain.member.exception.NoMemberException;
import com.minjae.doongstudy.domain.member.repository.MemberRepository;
import com.minjae.doongstudy.domain.thing.dto.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isEmpty())
            return LoginResponse.builder()
                    .apiResult(false)
                    .message("해당 사용자를 못찾았습니다")
                    .name(null)
                    .memberId(null)
                    .build();
        String password = loginRequest.getPassword();
        Member member = memberOptional.orElse(null);
        return member.getPassword().equals(password) ?
                LoginResponse.builder()
                        .apiResult(true)
                        .message("로그인에 성공했습니다!")
                        .name(member.getName())
                        .memberId(member.getMemberId())
                        .build() :
                LoginResponse.builder()
                        .apiResult(false)
                        .message("비밀번호가 올바르지 않습니다")
                        .name(null)
                        .memberId(null)
                        .build();
    }

    @Override
    @Transactional
    public RegisterResponse register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        Optional<Member> memberOptional = memberRepository.findByEmail(email);
        if (memberOptional.isPresent())
            throw new ExistMemberException();
        Member member = Member.from(registerRequest);
        member.setPassword(passwordEncoder.encode(member.getPassword()));
        Member saved = memberRepository.save(member);
        return RegisterResponse.builder()
                .apiResult(true)
                .memberId(saved.getMemberId())
                .build();
    }

    @Override
    public List<GetMemberResponse> getMembers() {
        List<Member> members = memberRepository.findAll();
        return members.stream().map(GetMemberResponse::from).toList();
    }

    @Override
    public GetMemberResponse getMemberById(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(NoMemberException::new);
        return GetMemberResponse.from(member);
    }

    @Override
    @Transactional
    public DeleteMemberResponse deleteMember(Long memberId) {
        memberRepository.findById(memberId)
                .orElseThrow(NoMemberException::new);
        memberRepository.deleteById(memberId);
        return DeleteMemberResponse.builder().apiResult(true).build();
    }
}
