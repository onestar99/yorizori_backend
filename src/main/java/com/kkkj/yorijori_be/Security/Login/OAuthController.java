package com.kkkj.yorijori_be.Security.Login;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;

    @GetMapping("/login/google")
    @ResponseBody
    public LoginDto GoogleLogin(@RequestParam("code") String accessCode) throws IOException {
        return oAuthService.googleLogin(accessCode);
    }


    @GetMapping("/login/kakao")
    @ResponseBody
    public LoginDto kakaoLogin(@RequestParam("code") String accessCode) throws IOException {
        return oAuthService.kakaoLogin(accessCode);
    }

    @GetMapping("/login/naver")
    @ResponseBody
    public LoginDto naverLogin(@RequestParam("code") String accessCode) throws IOException {
        return oAuthService.naverLogin(accessCode);
    }



}
