package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {


    private final UserService userService;




    // 유저 정보 저장
    @PostMapping("/save")
    public ResponseEntity saveUser(@RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return ResponseEntity.ok().body(userDto);
    }


    // 유저 정보 모두 조회
    // return -> json
    @GetMapping("/all") @ResponseBody
    public List<UserDto> getAllUser(){
        List<UserDto> userDtoList = userService.findAllUser();
        return userDtoList;
    }

    // 유저 정보 페이징으로 넘기기
//    @GetMapping("/all2")
//    public List<UserEntity> findByUserTokenId(@RequestParam String tokenId, Pageable pageable){
//
//        return userService.findAllUser();
//    }


    // 유저 tokenId를 통한 조회
    // return -> json
    @GetMapping("/{tokenId}") @ResponseBody
    public UserDto getBytokenId(@PathVariable("tokenId") String tokenId ) {
        return userService.findUserByTokenId(tokenId);
    }











}
