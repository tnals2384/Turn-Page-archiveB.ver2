package com.example.turnpage.domain.member.controller;

import com.example.turnpage.domain.member.dto.MemberSignupRequest;
import com.example.turnpage.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class AuthController {
    private final MemberService memberService;

    @PostMapping("/auth/signup")
    public @ResponseBody String signup(@RequestBody MemberSignupRequest requestDto) {

        Long memberId = memberService.signup(requestDto);
        return "회원가입에 성공하였습니다. 새로 등록된 회원의 memberId: " + memberId;
    }


    @PostMapping("/auth/login")
    public @ResponseBody String login() {

        return "hi";
    }
}
