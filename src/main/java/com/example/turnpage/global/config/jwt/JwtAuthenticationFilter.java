package com.example.turnpage.global.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j(topic = "JWT 인증")
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtils.getJwtFromHeader(req);
        //가져온 토큰이 유효한지 확인
        if(tokenValue != null) {
            if (jwtUtils.validateToken(tokenValue)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                Authentication authentication = jwtUtils.getAuthentication(tokenValue);
                context.setAuthentication(authentication);

                SecurityContextHolder.setContext(context);

            }
        }

        filterChain.doFilter(req, res);
    }
}
