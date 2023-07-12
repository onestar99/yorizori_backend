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

    // 유저 정보 삭제
    @GetMapping("") @ResponseBody
    public String deleteUser(
            @RequestParam(value = "tokenId", required = true) String tokenId
    ){
        userDeleteService.deleteUserById(tokenId);
        userDeleteService.deleteAllCommentById(tokenId);
        return "deleted";
    }

}
