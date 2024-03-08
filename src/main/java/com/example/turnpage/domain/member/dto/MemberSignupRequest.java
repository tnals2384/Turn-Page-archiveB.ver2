package com.example.turnpage.domain.member.dto;

import com.example.turnpage.domain.member.entity.Member;
import com.example.turnpage.domain.member.entity.Role;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberSignupRequest {
    private String username;
    private String password;

    public Member toEntity(String password) {
        return Member.builder()
                .name(username.split("@")[0])
                .email(username)
                .password(password)
                .role(String.valueOf(Role.USER))
                .inviteCode(RandomStringUtils.random(10, true, true))
                .build();
    }
}
