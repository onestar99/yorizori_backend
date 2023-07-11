package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;


    @GetMapping("/all")
    @ResponseBody
    public String getUser(){


        return "반갑슘다";
    }



    /*
    *
    *  front -> json 형식
    *  Method -> POST
    *
    * JSON 형식 Example
    * {
          "userTokenId" : "abcd1234",
          "nickName" : "윤하최고",
          "imageAddress" : "/src",
          "age" : "10대~20대",
          "gender" : "여자",
          "oauthDivision" : "google"
        }
    * */
    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return ResponseEntity.ok().body(userDto);
    }









}
