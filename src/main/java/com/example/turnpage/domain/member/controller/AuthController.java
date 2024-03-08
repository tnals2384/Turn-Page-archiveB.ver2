package com.example.turnpage.domain.member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AuthController {

    @GetMapping("/auth/login")
    public String login() {
        return "login";
    }

    @PostMapping("/auth/login")
    public @ResponseBody String loginProcess() {
        return "hi";
    }
}