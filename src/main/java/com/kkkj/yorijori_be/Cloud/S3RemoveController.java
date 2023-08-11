package com.kkkj.yorijori_be.Cloud;

import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class S3RemoveController {

    private final S3Remover s3Remover;
    private final UserSaveUpdateService userSaveUpdateService;


    @GetMapping("/remove/profile")
    public String removeProfileByAddress(@RequestParam String userId, @RequestParam String imageAddress){
        String result = s3Remover.deleteProfileImage(imageAddress); // 이미지 S3에서 삭제
        // 프로필 이미지 default로 수정 ->
        String defaultImage = "https://yorizori-s3.s3.ap-northeast-2.amazonaws.com/default/defaultProfile.png";
        userSaveUpdateService.updateProfile(userId, defaultImage);
        return result;
    }

    @PostMapping("/remove")
    public String removeRecipeByAddress(@RequestParam String imageAddress){
        String result = s3Remover.deleteFile(imageAddress); // 이미지 S3에서 삭제
        // 프로필 이미지 default로 수정 ->
        return result;
    }


}
