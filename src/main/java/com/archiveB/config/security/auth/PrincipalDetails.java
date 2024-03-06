package com.archiveB.config.security.auth;

import com.archiveB.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@Getter
public class PrincipalDetails implements OAuth2User, UserDetails {
    private User user;
    private Map<String, Object> attributes;

    public PrincipalDetails(User user) {
        this.user =user;
    }

    public PrincipalDetails(User user, Map<String, Object> attributes) {
        this.user= user;
        this.attributes = attributes;
    }

    @Override //사용자가 가지고 있는 권한 목록을 반환
    //사용자 이외의 권한이 없기에 user 권한만 담아 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getName() {
        return "name";
    }

    //사용자의 id(고유한 값) 반환
    //사용자를 식별할 수 있는 사용자 이름을 반환(
    @Override
    public String getUsername() {
        return user.getEmail();
    }


    //사용자의 비밀번호 반환
    @Override
    public String getPassword() {
        return user.getPassword();
    }


    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        //만료되었는지 확인하는 로직
        return true; //true -> 만료되지 않음
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        //계정 잠금되었는지 확인 하는 로직
        return true; //true -> 잠금되지 않았음
    }

    //패스워드 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        //패스워드가 만료되었는지 확인
        return true;
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        //계정이 사용 가능한지 확인하는 로직
        return true;
    }
}
