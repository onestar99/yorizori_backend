package com.kkkj.yorijori_be.Cloud;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class S3RemoveController {

    private final S3Remover s3Remover;

    // 이미지 S3 삭제
    @GetMapping("/remove")
    public String removeImageByAddress(@RequestParam String imageAddress){
        String result = s3Remover.deleteAbsoluteImage(imageAddress); // 이미지 S3에서 삭제
        return result;
    }

    // 이미지 여러개 삭제
    @GetMapping("/remove/all")
    @ResponseBody
    public String removeImagesByAddress(@RequestParam List<String> imagesAddress){
        String result = s3Remover.deleteAbsoluteImages(imagesAddress); // 이미지 S3에서 삭제
        return result;
    }


}
