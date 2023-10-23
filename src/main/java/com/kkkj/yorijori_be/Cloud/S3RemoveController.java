package com.kkkj.yorijori_be.Cloud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class S3RemoveController {

    private final S3Remover s3Remover;

    //프로필 이미지 S3 삭제 -> 절대주소로 삭제
    @DeleteMapping("/profileImage/remove")
    public String removeProfileImageByAbsoluteAddress(@RequestParam String imageAddress){
        String result = s3Remover.deleteAbsoluteImage(imageAddress); // 이미지 S3에서 삭제
        return result;
    }


    // 이미지 S3 삭제 -> 상대주소로 삭제
    @DeleteMapping("/image/remove")
    public String removeImageByAddress(@RequestParam String imageAddress){
        String result = s3Remover.deleteFile(imageAddress); // 이미지 S3에서 삭제
        return result;
    }



    // 이미지 여러개 삭제
    @PostMapping("/image/remove/all")
    @ResponseBody
    public String removeImagesByAddress(@RequestBody S3ImagesDto images){
        String result = s3Remover.deleteAbsoluteImages(images.getImages()); // 이미지 S3에서 삭제
        return result;
    }


}
