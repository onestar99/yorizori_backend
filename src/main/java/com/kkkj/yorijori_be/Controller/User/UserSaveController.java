package com.kkkj.yorijori_be.Controller.User;

import com.kkkj.yorijori_be.Dto.Tip.TipReviewSaveDto;
import com.kkkj.yorijori_be.Dto.User.UserCommentDto;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Service.Tip.TipSaveUpdateService;
import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequiredArgsConstructor
@RequestMapping("/user/save")
public class UserSaveController {

    private final UserSaveUpdateService userSaveUpdateService;
    private final TipSaveUpdateService tipSaveUpdateService;

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
        userSaveUpdateService.updateRecipeReviewCountAndStarCount(recipeId);
        return ResponseEntity.ok("User comment saved successfully : " + userCommentDto.getText());
    }

    @PostMapping("/tip/review/{tipId}")
    public ResponseEntity saveUserTipComment(@PathVariable Long tipId, @RequestBody TipReviewSaveDto tipReviewSaveDto){
        userSaveUpdateService.saveUserTipComment(tipId,tipReviewSaveDto);
        tipSaveUpdateService.updateReviewCount(tipId);
        return ResponseEntity.ok("User tipcomment saved");
    }

    @PostMapping("/tip/isHeart/{tipId}")
    public ResponseEntity saveTipIsHeart(
        @PathVariable Long tipId,
        @RequestParam (value = "userId",required = false) String userId,
        @RequestParam (value = "isHeart",required = false) boolean isHeart
    ){
        tipSaveUpdateService.saveTipInfo(tipId,userId,isHeart);
        return ResponseEntity.ok("Tip isHeart saved");
    }

}
