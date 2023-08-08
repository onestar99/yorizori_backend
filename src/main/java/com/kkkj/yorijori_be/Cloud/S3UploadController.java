package com.kkkj.yorijori_be.Cloud;

import com.kkkj.yorijori_be.Dto.Cloud.FileUploadResponse;
import com.kkkj.yorijori_be.Security.Status.DefaultRes;
import com.kkkj.yorijori_be.Security.Status.ResponseMessage;
import com.kkkj.yorijori_be.Security.Status.StatusCode;
import com.kkkj.yorijori_be.Service.User.UserSaveUpdateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class S3UploadController {
//저는 사용자 프로필을 업로드하기위해사용했으므로 이름이 UserApiController입니다.

    private final S3Uploader s3Uploader;
    private final UserSaveUpdateService userSaveUpdateService;

    //유저 프로필 업로드
    @PostMapping("user/update/profileImage/{userId}")
    public ResponseEntity uploadProfileImage(@PathVariable String userId, @RequestParam("profileImage") MultipartFile multipartFile) throws IOException {

        //S3 Bucket 내부에 "/userImage" 폴더
        FileUploadResponse fileUploadResponse = s3Uploader.uploadProfile(userId, multipartFile, "userImage");
        // 유저 프로필이미지 정보를 업데이트 시켜줘야함.
        String dbFileName = "https://yorizori-s3.s3.ap-northeast-2.amazonaws.com/" + fileUploadResponse.getFileName();

        userSaveUpdateService.updateProfile(userId, dbFileName);

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPLOAD_SUCCESS, fileUploadResponse), HttpStatus.OK);
    }

    //레시피 이미지 업로드
    @PostMapping("recipe/save/recipeImage/{recipeId}")
    public ResponseEntity uploadRecipeImage(@PathVariable Long recipeId, @RequestParam("recipeImage") List<MultipartFile> multipartFileList) throws IOException {

        //S3 Bucket 내부에 "/recipeImage" 폴더
        List<FileUploadResponse> fileUploadResponseList = s3Uploader.uploadRecipeImage(recipeId, multipartFileList, "src");
        // 레시피 List에 든 정보에다가 entity들을 업데이트 시켜줘야함.

        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.UPLOAD_SUCCESS, fileUploadResponseList), HttpStatus.OK);
    }

}