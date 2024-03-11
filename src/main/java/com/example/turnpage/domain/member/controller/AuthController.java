package com.example.turnpage.domain.member.controller;

import com.example.turnpage.domain.member.dto.CreateAccessTokenRequest;
import com.example.turnpage.domain.member.dto.CreateAccessTokenResponse;
import com.example.turnpage.domain.member.service.TokenService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AuthController {
    private final TokenService tokenService;

    @GetMapping("api/test")
    public @ResponseBody String authTest() {
        return "HI USER";
    }

    @GetMapping("/error")
    public @ResponseBody String authError() {
        return "접근 권한이 없습니다.";
    }
    
    
    //새로운 access Token을 발급
    @PostMapping("/auth/token")
    public ResponseEntity<CreateAccessTokenResponse> createNewAccessToken(@RequestBody CreateAccessTokenRequest request) {
        String newAccessToken = tokenService.createNewAccessToken(request.getRefreshToken());

        return ResponseEntity.status(HttpStatus.CREATED).body(new CreateAccessTokenResponse(newAccessToken));
    }

}
