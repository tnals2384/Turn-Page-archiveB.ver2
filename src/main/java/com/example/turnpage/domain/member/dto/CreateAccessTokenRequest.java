package com.example.turnpage.domain.member.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccessTokenRequest {
    private String refreshToken;
}
