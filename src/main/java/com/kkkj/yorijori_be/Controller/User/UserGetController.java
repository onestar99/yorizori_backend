package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserGetController {


    private final UserService userService;


    // 모든 유저 정보 조회
    // return -> json
    @GetMapping("/all") @ResponseBody
    public List<UserDto> getAllUser(){
        List<UserDto> userDtoList = userService.findAllUser();
        return userDtoList;
    }


    // 모든 유저 정보 페이징 처리
    @GetMapping("/all/paging") @ResponseBody
    public Page<UserEntity> getAllPaging(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "user_token_id", required = false) String sortBy
            ){
        return userService.getUserPaging(pageNo, pageSize, sortBy);
    }


    // 유저 tokenId를 통한 조회
    // return -> json
    @GetMapping("/{tokenId}") @ResponseBody
    public UserDto getByUserTokenId(@PathVariable("tokenId") String tokenId ) {
        return userService.findUserByTokenId(tokenId);
    }


}
