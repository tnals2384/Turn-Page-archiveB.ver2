package com.example.turnpage.domain.member.service;

import com.example.turnpage.domain.member.entity.Member;
import com.example.turnpage.global.config.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

@RequiredArgsConstructor
@Service
public class TokenService {

    private final RefreshTokenService refreshTokenService;
    private final JwtUtils jwtUtils;
    private final MemberService memberService;

    public String createNewAccessToken(String refreshToken) {
        if (!jwtUtils.validateToken(refreshToken)) {
            throw new IllegalArgumentException("Unexpected token");
        }

        Long memberId = refreshTokenService.findByRefreshToken(refreshToken).getMemberId();
        Member member = memberService.findById(memberId);

        return jwtUtils.generateToken(member, Duration.ofMinutes(30));
    }
}
