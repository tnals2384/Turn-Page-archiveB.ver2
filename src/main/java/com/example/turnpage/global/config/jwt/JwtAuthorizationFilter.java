package com.example.turnpage.global.config.jwt;

import com.example.turnpage.global.config.security.MemberDetailsService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 검증 및 인가")
@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtils.getJwtFromHeader(req);
        //가져온 토큰이 유효한지 확인
        if (StringUtils.hasText(tokenValue)) {
            if (!jwtUtils.validateToken(tokenValue)) {
                log.error("Token Error");
                return;
            }

            //유효한 토큰일 경우 인증 정보 설정
            try {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authentication = jwtUtils.getAuthentication(tokenValue);
                context.setAuthentication(authentication);

                SecurityContextHolder.setContext(context);


            } catch (Exception e) {
                log.error(e.getMessage());
                return;
            }
        }

        filterChain.doFilter(req, res);
    }
}
