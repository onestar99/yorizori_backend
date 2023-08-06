package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Dto.User.UserCommentDto;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user/save")
public class UserSaveController {

    private final UserSaveUpdateService userSaveUpdateService;

    // 유저 정보 저장
    @PostMapping("")
    public ResponseEntity saveUser(@RequestBody UserDto userDto){
        userSaveUpdateService.saveUser(userDto);
        return ResponseEntity.ok().body(userDto);
    }


    @PostMapping("/review/{recipeId}")
    public ResponseEntity saveUserComment(@PathVariable Long recipeId, @RequestBody UserCommentDto userCommentDto) {
        // 인자로 userTokenId와 Dto를 넘겨서 save해주는 함수 만들기.
        userSaveUpdateService.saveUserComment(recipeId, userCommentDto);
        return ResponseEntity.ok("User comment saved successfully : " + userCommentDto.getText());
    }

}
