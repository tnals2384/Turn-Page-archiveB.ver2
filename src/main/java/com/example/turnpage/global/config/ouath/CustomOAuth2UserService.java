package com.example.turnpage.global.config.ouath;

import com.example.turnpage.domain.member.entity.Member;
import com.example.turnpage.domain.member.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.Set;


@Service
@Transactional
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final MemberRepository memberRepository;


    //유저의 정보를 받아오는 함수
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService<OAuth2UserRequest, OAuth2User> service = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = service.loadUser(userRequest);

        Map<String, Object> originAttributes = oAuth2User.getAttributes();

        //oAuth2 서비스 id (google, kakao)
        String registrationId = userRequest.getClientRegistration().getRegistrationId(); //소셜 정보 가져오기

        //attribute를 서비스 유형에 맞게 담음
        OAuthAttributes attributes = OAuthAttributes.of(registrationId, originAttributes);
        Member member = saveOrUpdate(registrationId, attributes);
        Set<GrantedAuthority> authorities = Collections.singleton(new SimpleGrantedAuthority(member.getRole().toString()));

        return new CustomOAuth2User(authorities, attributes.getAttributes(), attributes.getNameAttributesKey(), attributes);

    }

    //이미 존재하는 회원이라면 이름과 프로필 사진 업데이트,
    //처음 가입한다면 DB에 생성
    private Member saveOrUpdate(String registrationId, OAuthAttributes attributes) {
        Member member = memberRepository.findByEmail(attributes.getEmail())
                .map(entity -> entity.update(attributes.getName(), attributes.getProfileImage()))
                .orElse(attributes.toEntity(registrationId.toUpperCase()));

        return memberRepository.save(member);
    }


}
