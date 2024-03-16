package com.example.turnpage.global.config.jwt;

import com.example.turnpage.domain.member.entity.Member;
import com.example.turnpage.global.config.security.MemberDetails;
import com.example.turnpage.global.config.security.MemberDetailsService;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Duration;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

@Slf4j(topic = "JwtUtil")
@Component
@RequiredArgsConstructor
public class JwtUtils {
    /* 1. JWT 데이터 준비하기 */
    public static final String AUTHORIZATION_HEADER = "Authorization";      // Header KEY 값
    public static final String BEARER_PREFIX = "Bearer ";       // Token 식별자
    @Value("${jwt.secret_key}")
    private String jwtSecretKey;

    private final MemberDetailsService memberDetailsService;

    public String generateToken(Member member, Duration expiredAt) {
        Date now = new Date();
        return makeToken(new Date(now.getTime() + expiredAt.toMillis()), member);
    }

    //JWT 토큰 생성
    private String makeToken(Date expiry, Member member) {
        Date now = new Date();

        return Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE) //typ: JWT
                //payload값
                .setIssuedAt(now) // iat: 토큰 발행 시간
                .setExpiration(expiry) // exp: 만료 시간
                .setSubject(member.getEmail()) // sub: 유저의 이메일
                //서명 : 비밀값과 해시값을 HS256 방식으로 암호화
                .signWith(SignatureAlgorithm.HS256, jwtSecretKey)
                .compact();
    }

    //토큰 값 검증
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecretKey).parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    //토큰 기반으로 인증 정보를 가져오는 메서드
    //클레임 정보를 반환 받아 getSubject()를 통해 토큰 제목 sub와 토큰 기반 인증 정보를 생성
    public Authentication getAuthentication(String token) {
        String sub = getClaims(token).getSubject();

        MemberDetails memberDetails = memberDetailsService.loadUserByUsername(sub);
        return new UsernamePasswordAuthenticationToken(
                memberDetails, token, memberDetails.getAuthorities());
    }

    //비밀 값으로 복호화 한 다음 클레임 정보 반환
    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecretKey)
                .parseClaimsJws(token)
                .getBody(); //payload에 해당하는 부분의 정보 반환
    }

    //헤더에서 jwt 토큰 값 받아오기
    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

}
