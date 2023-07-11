package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/update")
public class UserUpdateController {


    private final UserSaveUpdateService userSaveUpdateService;


    // 토큰 아이디를 통한 닉네임 수정
    // return 변경된 닉네임
    @GetMapping("") @ResponseBody
    public String updateNickName(
            @RequestParam(value = "tokenId", required = true) String tokenId,
            @RequestParam(value = "nickName", required = true) String nickName
    ){
        userSaveUpdateService.updateNickNameById(tokenId, nickName);
        return nickName;
    }

}
