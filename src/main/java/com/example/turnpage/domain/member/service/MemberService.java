package com.example.turnpage.domain.member.service;

import com.example.turnpage.domain.member.dto.MemberSignupRequestDto;
import com.example.turnpage.domain.member.entity.Member;
import com.example.turnpage.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long signup(MemberSignupRequestDto memberSignupRequestDto) {
        // 이메일 중복 확인 코드
        //

        String encodedPassword = passwordEncoder.encode(memberSignupRequestDto.getPassword());
        memberSignupRequestDto.setPassword(encodedPassword);

        Member member = memberSignupRequestDto.toEntity();
        Long memberId = memberRepository.save(member).getId();
        return memberId;
    }
}
