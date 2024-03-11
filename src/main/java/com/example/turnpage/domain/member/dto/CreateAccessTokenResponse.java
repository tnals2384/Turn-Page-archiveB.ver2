package com.example.turnpage.domain.member.dto;

import com.example.turnpage.domain.member.entity.Member;
import com.example.turnpage.domain.member.entity.Role;
import lombok.*;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAccessTokenResponse {
    private String accessToken;
}
