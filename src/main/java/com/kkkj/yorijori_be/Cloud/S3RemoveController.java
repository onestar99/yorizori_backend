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


    // 이미지 S3 삭제
    @GetMapping("/remove")
    public String removeImageByAddress(@RequestParam String imageAddress){
        String result = s3Remover.deleteProfileImage(imageAddress); // 이미지 S3에서 삭제
        return result;
    }



}
