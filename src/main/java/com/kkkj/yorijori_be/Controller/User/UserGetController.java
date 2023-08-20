package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Entity.User.UserCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Entity.User.UserTipCommentEntity;
import com.kkkj.yorijori_be.Entity.User.UserViewLogEntity;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import com.kkkj.yorijori_be.Service.User.UserGetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/get")
public class UserGetController {


    private final UserGetService userGetService;
    private final UserRepository userRepository;


    // 모든 유저 정보 조회
    // return -> json
    @GetMapping("/all") @ResponseBody
    public List<UserDto> getAllUser(){
        List<UserDto> userDtoList = userGetService.findAllUser();
        return userDtoList;
    }


    // 모든 유저 정보 페이징 처리
    @GetMapping("/all/paging") @ResponseBody
    public Page<UserEntity> getAllPaging(
            @RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = "3", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "user_token_id", required = false) String sortBy
            ){
        return userGetService.getUserPaging(pageNo, pageSize, sortBy);
    }


    // 유저 tokenId를 통한 조회
    // return -> json
    @GetMapping("/{userTokenId}") @ResponseBody
    public UserDto getByUserTokenId(@PathVariable("userTokenId") String userTokenId ) {
        return userGetService.findUserByTokenId(userTokenId);
    }



    @GetMapping("/{userTokenId}/comments") @ResponseBody
    public ResponseEntity<List<UserCommentEntity>> getUserComments(@PathVariable String userTokenId) {
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        List<UserCommentEntity> comments = user.getComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/{userTokenId}/tipcomments") @ResponseBody
    public ResponseEntity<List<UserTipCommentEntity>> getUserTipComments(@PathVariable String userTokenId) {
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        List<UserTipCommentEntity> tipcomments = user.getTipComments();
        return ResponseEntity.ok(tipcomments);
    }

    @GetMapping("/{userTokenId}/viewlog") @ResponseBody
    public ResponseEntity<List<UserViewLogEntity>> getUserViewLog(@PathVariable String userTokenId) {
        UserEntity user = userRepository.findByUserTokenId(userTokenId);
        List<UserViewLogEntity> viewLog = user.getViewLog();
        return ResponseEntity.ok(viewLog);
    }



}
