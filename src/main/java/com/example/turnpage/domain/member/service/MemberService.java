package com.example.turnpage.domain.member.service;

import com.example.turnpage.domain.member.entity.Member;
import com.example.turnpage.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;


    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(IllegalArgumentException::new);
    }
}
