package com.kkkj.yorijori_be.Cloud;

import com.kkkj.yorijori_be.Dto.Cloud.FileUploadResponse;
import com.kkkj.yorijori_be.Security.Status.DefaultRes;
import com.kkkj.yorijori_be.Security.Status.ResponseMessage;
import com.kkkj.yorijori_be.Security.Status.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class S3UploadController {
//저는 사용자 프로필을 업로드하기위해사용했으므로 이름이 UserApiController입니다.

    private final S3Uploader s3Uploader;

    //유저 프로필 업로드
    @PostMapping("user/save/profilePhoto")
    public ResponseEntity uploadProfilePhoto(@RequestParam("profilePhoto") MultipartFile multipartFile) throws IOException {

        //S3 Bucket 내부에 "/profile" 폴더
        FileUploadResponse fileUploadResponse = s3Uploader.upload("123", multipartFile, "profile");
        return new ResponseEntity(DefaultRes.res(StatusCode.OK, ResponseMessage.LOGIN_SUCCESS, fileUploadResponse), HttpStatus.OK);
    }
}