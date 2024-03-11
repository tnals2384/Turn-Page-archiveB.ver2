package com.example.turnpage.global.config;

import com.example.turnpage.domain.member.repository.RefreshTokenRepository;
import com.example.turnpage.domain.member.service.MemberService;
import com.example.turnpage.global.config.jwt.CustomAccessDeniedHandler;
import com.example.turnpage.global.config.jwt.CustomAuthenticationEntryPoint;
import com.example.turnpage.global.config.jwt.JwtAuthenticationFilter;
import com.example.turnpage.global.config.jwt.JwtUtils;
import com.example.turnpage.global.config.ouath.CustomOAuth2UserService;
import com.example.turnpage.global.config.ouath.OAuth2AuthorizationRequestBasedOnCookieRepository;
import com.example.turnpage.global.config.ouath.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtUtils jwtUtils;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final MemberService memberService;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public OAuth2AuthorizationRequestBasedOnCookieRepository oAuth2AuthorizationRequestBasedOnCookieRepository
            () {
        return new OAuth2AuthorizationRequestBasedOnCookieRepository();
    }

    @Bean
    public OAuth2SuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(jwtUtils,
                refreshTokenRepository, memberService, oAuth2AuthorizationRequestBasedOnCookieRepository());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtils), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception.accessDeniedHandler(accessDeniedHandler)
                        .authenticationEntryPoint(authenticationEntryPoint))
                .oauth2Login(oauth2 ->
                        oauth2.authorizationEndpoint(authorization -> authorization.authorizationRequestRepository(
                                        oAuth2AuthorizationRequestBasedOnCookieRepository()))
                                .successHandler(oAuth2SuccessHandler())
                                .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService)))
                .build();
    }
}
