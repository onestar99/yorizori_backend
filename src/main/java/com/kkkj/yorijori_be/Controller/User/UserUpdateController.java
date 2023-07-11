package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/update")
public class UserUpdateController {


    private final UserSaveUpdateService userSaveUpdateService;


    // 토큰 아이디를 통한 닉네임 수정
    // return 변경된 닉네임
    @GetMapping("/{tokenId}") @ResponseBody
    public String updateNickName(@PathVariable String tokenId){

        userSaveUpdateService.updateNickNameById(tokenId, "할게많네요");
        return "할게많네요";
    }

}
