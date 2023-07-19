package com.kkkj.yorijori_be.Cloud;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kkkj.yorijori_be.Dto.Cloud.FileUploadResponse;
import com.kkkj.yorijori_be.Entity.User.UserEntity;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public FileUploadResponse upload(String userId, MultipartFile multipartFile, String dirName) throws IOException {

        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));

        return upload(userId, uploadFile, dirName);
    }


    private FileUploadResponse upload(String userId, File uploadFile, String dirName) {

        log.info("파일 확장자 : " + getExtension(uploadFile));
        String Extension = getExtension(uploadFile);
        /*
        * TODO 여기에 PNG, JPEG 사진 파일만을 거르도록 해주는 함수 추가하기.
        *
        * */

        if (Objects.equals(Extension, "png") || Objects.equals(Extension, "jpeg")){

            // UUID 생성하여 이미지 네이밍
            UUID uuid4 = UUID.randomUUID();
            String fileName = dirName + "/" + uuid4 + "." + getExtension(uploadFile);
            String uploadImageUrl = putS3(uploadFile, fileName);
            removeNewFile(uploadFile);
            // 프로필 등록 아직 테스트중.
    //        UserEntity user = userRepository.findById(userId).get();
    //        user.setProfilePhoto(uploadImageUrl);

    //FileUploadResponse DTO로 반환해준다.
            return new FileUploadResponse(fileName, uploadImageUrl);
            //return uploadImageUrl;
        } else {
            return new FileUploadResponse("null", "null");
        }


    }


    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 로컬에서 삭제되었습니다.");
        } else {
            log.info("파일이 로컬에서 삭제되지 못했습니다.");
        }
    }

    /*
    * MultipartFile -> File 전환
    *
    * why? -> S3는 MultipartFile 타입이 전송이 안됨.
    * */
    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if(convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }



    public String getExtension(File File) {
        String fileName = File.getName();
        log.info(fileName);
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension;
    }


}

