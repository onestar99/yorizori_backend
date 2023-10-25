package com.kkkj.yorijori_be.Cloud;

import com.kkkj.yorijori_be.Dto.Cloud.FileUploadResponse;
import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Security.Status.DefaultRes;
import com.kkkj.yorijori_be.Security.Status.ResponseMessage;
import com.kkkj.yorijori_be.Security.Status.StatusCode;
import com.kkkj.yorijori_be.Service.Recipe.RecipeSaveUpdateService;
import com.kkkj.yorijori_be.Service.User.UserGetService;
import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3UploadController {
//저는 사용자 프로필을 업로드하기위해사용했으므로 이름이 UserApiController입니다.

    private final S3Uploader s3Uploader;
    private final UserSaveUpdateService userSaveUpdateService;
    private final RecipeSaveUpdateService recipeSaveUpdateService;
    private final UserGetService userGetService;
    private final S3Remover s3Remover;


    // 유저 프로필 이미지 템프 업로드
    @PostMapping("/image/upload/profile")
    public ResponseEntity uploadProfileTemp(@RequestParam("profileImage") MultipartFile multipartFile) throws IOException {
        //S3 Bucket 내부에 "userImage" 폴더
        FileUploadResponse fileUploadResponse = s3Uploader.uploadProfile(multipartFile, "userImage");
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPLOAD_SUCCESS, fileUploadResponse), HttpStatus.OK);
    }

    // 레시피 이미지 템프 업로드
    @PostMapping("/image/upload/recipe")
    public ResponseEntity uploadRecipeImageTemp(@RequestParam("recipeImage") MultipartFile multipartFile) throws IOException {
        //S3 Bucket 내부에 "src" 폴더
        FileUploadResponse fileUploadResponse = s3Uploader.uploadImage(multipartFile, "recipe");
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPLOAD_SUCCESS, fileUploadResponse), HttpStatus.OK);

    }

    // 팁 이미지 템프 업로드
    @PostMapping("/image/upload/tip")
    public ResponseEntity uploadTipImageTemp(@RequestParam("tipImage") MultipartFile multipartFile) throws IOException {
        //S3 Bucket 내부에 "tip" 폴더
        FileUploadResponse fileUploadResponse = s3Uploader.uploadImage(multipartFile, "tip");
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPLOAD_SUCCESS, fileUploadResponse), HttpStatus.OK);

    }

    // Get 이미지 주소,  유저 토큰아이디를 받아서 MyPage 양식 반환.
    @GetMapping("/profile/apply")
    public UserDto applyImage(@RequestParam(value = "userId", required = false)String userId,
                              @Nullable@RequestParam("postNickname")String postNickName,
                              @Nullable@RequestParam(value = "postImage", required = false)String postImage,
                              @Nullable@RequestParam(value = "gender", required = false)String gender,
                              @Nullable@RequestParam(value = "age", required = false)String age) throws IOException {
        return userSaveUpdateService.updateProfileById(userId, postNickName, postImage, gender, age);
    }


}