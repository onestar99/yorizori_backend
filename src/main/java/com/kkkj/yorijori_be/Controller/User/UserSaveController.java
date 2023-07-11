package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserSaveController {

    private final UserSaveUpdateService userSaveUpdateService;

    // 유저 정보 저장
    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody UserDto userDto){
        userSaveUpdateService.saveUser(userDto);
        return ResponseEntity.ok().body(userDto);
    }




}
