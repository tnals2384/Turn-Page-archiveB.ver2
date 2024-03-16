package com.example.turnpage.global.config.ouath;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import java.util.Collection;
import java.util.Map;


public class CustomOAuth2User extends DefaultOAuth2User {
    private final OAuthAttributes oAuthAttributes;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities, Map<String, Object> attributes, String nameAttributeKey,
                            OAuthAttributes oAuthAttributes) {
        super(authorities,attributes,nameAttributeKey);
        this.oAuthAttributes = oAuthAttributes;
    }

    public String getEmail() {
        return this.oAuthAttributes.getEmail();
    }

}
