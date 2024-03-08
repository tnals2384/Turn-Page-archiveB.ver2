package com.example.turnpage.global.config.jwt;

import com.example.turnpage.domain.member.dto.MemberLoginRequest;
import com.example.turnpage.domain.member.entity.Member;
import com.example.turnpage.global.config.security.MemberDetails;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.time.Duration;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
        setFilterProcessesUrl("/auth/login"); // 해당 경로로 요청이 들어왔을 때,
    }

    // 로그인 시, username 과 password 를 바탕으로 UsernamePasswordAuthenticationToken 을 발급
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            //request의 inputstream값을 읽어서 MemberLoginRequestDto 생성
            MemberLoginRequest requestDto = new ObjectMapper().readValue(request.getInputStream(), MemberLoginRequest.class);

            //authentication 객체를 생성 (UsernamePasswordAuthenticationToken), authenticationManager가 인증을 진행하도록함
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword(),null)
            );
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    // 로그인 성공 시
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws ServletException, IOException {
        Member member = ((MemberDetails) authResult.getPrincipal()).getMember();

        String token = jwtUtils.generateToken(member, Duration.ofMinutes(30)); //토큰 생성
        response.addHeader(JwtUtils.AUTHORIZATION_HEADER, token);  //생성한 토큰을 헤더에 추카

    }

    // 로그인 실패 시
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }
}
