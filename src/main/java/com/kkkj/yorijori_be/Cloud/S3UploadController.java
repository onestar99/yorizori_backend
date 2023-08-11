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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class S3UploadController {
//저는 사용자 프로필을 업로드하기위해사용했으므로 이름이 UserApiController입니다.

    private final S3Uploader s3Uploader;
    private final UserSaveUpdateService userSaveUpdateService;
    private final RecipeSaveUpdateService recipeSaveUpdateService;
    private final UserGetService userGetService;
    private final S3Remover s3Remover;

    //유저 프로필 업로드 후 유저 테이블에서 프로필 업데이트.
    @PostMapping("user/update/profileImage/{userId}")
    public ResponseEntity uploadProfileImage(@PathVariable String userId, @RequestParam("profileImage") MultipartFile multipartFile) throws IOException {

        //S3 Bucket 내부에 "/userImage" 폴더
        FileUploadResponse fileUploadResponse = s3Uploader.uploadProfile(userId, multipartFile, "userImage");
        // 유저 프로필이미지 정보를 업데이트
        String dbFileName = "https://yorizori-s3.s3.ap-northeast-2.amazonaws.com/" + fileUploadResponse.getFileName();
        userSaveUpdateService.updateProfile(userId, dbFileName);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPLOAD_SUCCESS, fileUploadResponse), HttpStatus.OK);
    }

    //레시피 썸네일 이미지 업로드 후 레시피 테이블에서 썸네일 업데이트.
    @PostMapping("/recipe/save/thumbnail/{recipeId}")
    public ResponseEntity uploadRecipeThumbnailImage(@PathVariable Long recipeId, @RequestParam("recipeImage") MultipartFile multipartFile) throws IOException {

        //S3 Bucket 내부에 "src" 폴더
        FileUploadResponse fileUploadResponse = s3Uploader.uploadImage(multipartFile, "src");
        // 레시피 이미지 정보를 업데이트
        String dbFileName = fileUploadResponse.getFileName();
        recipeSaveUpdateService.updateThumbnail(recipeId, dbFileName);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPLOAD_SUCCESS, fileUploadResponse), HttpStatus.OK);
    }

    //레시피 이미지들 업로드 후 레시피 디테일 테이블에서 이미지들 업데이트.
    @PostMapping("recipe/save/recipeImages/{recipeId}")
    public ResponseEntity uploadRecipeImages(@PathVariable Long recipeId, @RequestParam("recipeImages") List<MultipartFile> multipartFileList) throws IOException {

        //S3 Bucket 내부에 "/recipeImage" 폴더
        List<FileUploadResponse> fileUploadResponseList = s3Uploader.uploadImages(multipartFileList, "src");
        // 레시피 List에 든 정보에다가 entity들을 업데이트 시켜줘야함.
        recipeSaveUpdateService.updateRecipeDetailImage(recipeId, fileUploadResponseList);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPLOAD_SUCCESS, fileUploadResponseList), HttpStatus.OK);
    }
    //이미지 템프 업로드
    @PostMapping("/image/upload")
    public ResponseEntity uploadRecipeTemp(@RequestParam("recipeImage") MultipartFile multipartFile) throws IOException {
        //S3 Bucket 내부에 "src" 폴더
        FileUploadResponse fileUploadResponse = s3Uploader.uploadImage(multipartFile, "src");
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPLOAD_SUCCESS, fileUploadResponse), HttpStatus.OK);

    }

    // Get 이미지 주소, 유저 토큰아이디를 받아서 MyPage 양식 반환.
    @GetMapping("/image/apply")
    public UserDto applyImage(@RequestParam("userId")String userId, @RequestParam("postImage")String postImage) throws IOException {

        userSaveUpdateService.updateProfile(userId, postImage); // 프로필 업데이트
        return userGetService.findUserByTokenId(userId);
    }


}