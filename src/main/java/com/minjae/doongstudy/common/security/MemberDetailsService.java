package com.minjae.doongstudy.common.security;

import com.minjae.doongstudy.domain.member.entity.Member;
import com.minjae.doongstudy.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByEmail(username);
        if (memberOptional.isEmpty())
            throw new UsernameNotFoundException("이메일 사용자가 없습니다");
        return new MemberDetails(memberOptional.orElse(null));
    }
}
