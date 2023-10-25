package com.kkkj.yorijori_be.Security.Login;

import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class OAuthController {

    private final OAuthService oAuthService;
    private final UserRepository userRepository;

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

    @GetMapping("/logout/kakao")
    @ResponseBody
    public ResponseEntity<String> kakaoLogout(@RequestParam("code") String accessCode, @RequestParam("id") long userTokenId) throws IOException {
        return oAuthService.kakaoLogout(accessCode, userTokenId);
    }

    @GetMapping("/login/naver")
    @ResponseBody
    public LoginDto naverLogin(@RequestParam("code") String accessCode) throws IOException {
        return oAuthService.naverLogin(accessCode);
    }

    @GetMapping("/unlink")
    @ResponseBody
    public ResponseEntity<String> Unlink(@RequestParam("code") String accessCode, @RequestParam("id") String userTokenId) throws IOException {

        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        if(user == null){
            return null;
        }

        String site = user.getOauthDivision();
        if(site.equals("kakao"))
            return oAuthService.kakaoUnlink(user, accessCode, userTokenId);
        else if(site.equals("naver")){
            return null;
        }
        else{ // 구글
            return null;
        }
    }



}
