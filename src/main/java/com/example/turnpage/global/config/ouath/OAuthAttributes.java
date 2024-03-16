package com.example.turnpage.global.config.ouath;

import com.example.turnpage.domain.member.entity.Member;
import com.example.turnpage.domain.member.entity.Role;
import com.example.turnpage.domain.member.entity.SocialType;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
@Builder
public class OAuthAttributes {
    private Map<String, Object> attributes;  //oAuth2 반환하는 유저의 정보
    private String nameAttributesKey;
    private String name;
    private String email;
    private String profileImage;

    public static OAuthAttributes of(String socialName, Map<String, Object> attributes) {
        if ("kakao".equals(socialName))
            return ofKakao("id", attributes);
        else if ("google".equals(socialName))
            return ofGoogle("sub", attributes);

        return null;
    }

    private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .name((String) attributes.get("name"))
                .email((String) attributes.get("email"))
                .profileImage((String) attributes.get("picture"))
                .attributes(attributes)
                .nameAttributesKey(userNameAttributeName)
                .build();
    }

    private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> kakaoProfile = (Map<String, Object>) kakaoAccount.get("profile");
        return OAuthAttributes.builder()
                .email(String.valueOf(kakaoAccount.get("email")))
                .name(String.valueOf(kakaoProfile.get("nickname")))
                .profileImage(String.valueOf(kakaoProfile.get("profile_image_url")))
                .nameAttributesKey(userNameAttributeName)
                .attributes(attributes)
                .build();

    }

    public Member toEntity(String registrationId) {
        return Member.builder()
                .name(name)
                .email(email)
                .image(profileImage)
                .role(Role.USER)
                .socialType(SocialType.valueOf(registrationId))
                .build();
    }
}
