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


    // 프로필 S3 삭제
    @GetMapping("/remove/profile")
    public String removeProfileByAddress(@RequestParam String imageAddress){
        String result = s3Remover.deleteProfileImage(imageAddress); // 이미지 S3에서 삭제
        return result;
    }

    // 일반 이미지 S3 삭제
    @PostMapping("/remove/recipe")
    public String removeRecipeByAddress(@RequestParam String imageAddress){
        String result = s3Remover.deleteFile(imageAddress); // 이미지 S3에서 삭제
        return result;
    }


}
