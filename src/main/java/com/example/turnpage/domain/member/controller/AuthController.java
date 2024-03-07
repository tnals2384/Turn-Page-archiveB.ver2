package com.example.turnpage.domain.member.controller;

import com.example.turnpage.domain.member.dto.MemberSignupRequestDto;
import com.example.turnpage.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequiredArgsConstructor
@Controller
public class AuthController {
    private final MemberService memberService;

    @GetMapping("/auth/signup")
    public String signup() {
        return "signup";
    }

    @PostMapping("/auth/signup")
    @ResponseBody
    public String signup(@RequestParam String username, String password) {
        MemberSignupRequestDto memberSignupRequestDto = new MemberSignupRequestDto();
        memberSignupRequestDto.setUsername(username);
        memberSignupRequestDto.setPassword(password);

        Long memberId = memberService.signup(memberSignupRequestDto);
        return "회원가입에 성공하였습니다. 새로 등록된 회원의 memberId: " + memberId;
    }

    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }

    @PostMapping("/auth/login")
    public @ResponseBody String loginProcess() {
        return "hi";
    }

    @GetMapping("/public")
    public @ResponseBody String publicAccess() {
        return "이 페이지는 모든 사용자가 이용할 수 있습니다. 당신은 로그인하지 않았을 것 같네요 :<";
    }

    @GetMapping("/private")
    public @ResponseBody String privateAccess() {
        return "이 페이지는 로그인된 사용자만 이용할 수 있습니다. 당신은 로그인한 상태네요? :>";
    }
}
