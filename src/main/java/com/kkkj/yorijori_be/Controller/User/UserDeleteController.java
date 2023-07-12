package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Service.User.UserDeleteService;
import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/delete")
public class UserDeleteController {

    private final UserDeleteService userDeleteService;

    // 유저 정보 저장
    @GetMapping("") @ResponseBody
    public String deleteNickName(
            @RequestParam(value = "tokenId", required = true) String tokenId,
            @RequestParam(value = "nickName", required = true) String nickName
    ){
        userDeleteService.deleteNickNameById(tokenId, nickName);
        return "deleted";
    }

}
