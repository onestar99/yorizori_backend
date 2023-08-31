package com.kkkj.yorijori_be.Cloud;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.kkkj.yorijori_be.Dto.Cloud.FileUploadResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.cert.Extension;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Component
public class S3Uploader {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;


    // 프로필 이미지 1개 저장하기
    public FileUploadResponse uploadProfile(MultipartFile multipartFile, String dirName) throws IOException {

        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        return uploadS3ProfileImage(uploadFile, dirName);

    }
    // 이미지 1개 저장하기
    public FileUploadResponse uploadImage(MultipartFile multipartFile, String dirName) throws IOException {

        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
        return uploadS3Image(uploadFile, dirName);

    }

    // 레시피 이미지 여러개 저장하기.
    public List<FileUploadResponse> uploadImages(List<MultipartFile> multipartFileList, String dirName) throws IOException {

        List<FileUploadResponse> fileUploadResponseList = new ArrayList<>();

        for(MultipartFile multipartFile: multipartFileList){
            File uploadFile = convert(multipartFile)
                    .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File로 전환이 실패했습니다."));
            fileUploadResponseList.add(uploadS3Images(uploadFile, dirName));
        }
        return fileUploadResponseList;
    }


    private FileUploadResponse uploadS3ProfileImage(File uploadFile, String dirName) {

        log.info("파일 확장자 : " + getExtension(uploadFile));
        String Extension = getExtension(uploadFile);

        if (Objects.equals(Extension, "png") || Objects.equals(Extension, "jpeg") || Objects.equals(Extension, "jpg")){

            // UUID 생성하여 이미지 네이밍
            UUID uuid4 = UUID.randomUUID();
            String fileName = dirName + "/" + uuid4 + "." + getExtension(uploadFile);
            String uploadImageUrl = putS3(uploadFile, fileName);
            removeNewFile(uploadFile);

            //FileUploadResponse DTO로 반환해준다.
            return new FileUploadResponse("/" + fileName, uploadImageUrl);
        } else {
            removeNewFile(uploadFile);
            log.warn("업로드 형식이 올바르지 않습니다.");
            return new FileUploadResponse("null", "null");
        }
    }

    private FileUploadResponse uploadS3Image(File uploadFile, String dirName) {

        log.info("파일 확장자 : " + getExtension(uploadFile));
        String Extension = getExtension(uploadFile);

        if (fileExtensionConfirm(Extension)){

            // UUID 생성하여 이미지 네이밍
            UUID uuid4 = UUID.randomUUID();
            String fileName = dirName + "/" + uuid4 + "." + getExtension(uploadFile);
            String uploadImageUrl = putS3(uploadFile, fileName);
            removeNewFile(uploadFile);

            //FileUploadResponse DTO로 반환해준다.
            return new FileUploadResponse("/" + fileName, uploadImageUrl);
        } else {
            removeNewFile(uploadFile);
            log.warn("업로드 형식이 올바르지 않습니다.");
            return new FileUploadResponse("null", "null");
        }
    }

    private FileUploadResponse uploadS3Images(File uploadFile, String dirName) {

        log.info("파일 확장자 : " + getExtension(uploadFile));
        String Extension = getExtension(uploadFile);
        /*
        * TODO PNG, JPEG 사진 파일만을 거르도록 해주는 함수 추가하기.
        * -완료-
        * */

        if (fileExtensionConfirm(Extension)){

            // UUID 생성하여 이미지 네이밍
            UUID uuid4 = UUID.randomUUID();
            String fileName = dirName + "/" + uuid4 + "." + getExtension(uploadFile);
            String uploadImageUrl = putS3(uploadFile, fileName);
            removeNewFile(uploadFile); // 파일 삭제

            //FileUploadResponse DTO로 반환해준다.
            return new FileUploadResponse("/" + fileName, uploadImageUrl);
        } else {
            removeNewFile(uploadFile);
            return new FileUploadResponse(null, null);
        }


    }

    // S3로 파일 올리기
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
                CannedAccessControlList.PublicRead));
        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // 로컬에서 만들어진 파일 삭제
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



    // 파일의 확장자 명칭을 return.
    // Ex) png, jpeg, exe
    public String getExtension(File File) {
        String fileName = File.getName();
        log.info(fileName);
        String extension = fileName.substring(fileName.lastIndexOf(".") + 1);
        return extension;
    }


    // 파일 확장자 확인 검증
    private boolean fileExtensionConfirm(String Extension){

        ArrayList<String> stringArrayList = new ArrayList<String>();
        stringArrayList.add("png");
        stringArrayList.add("PNG");
        stringArrayList.add("jpeg");
        stringArrayList.add("JPEG");
        stringArrayList.add("jpg");
        stringArrayList.add("JPG");

        for(String str: stringArrayList){
            if (Objects.equals(Extension, str)) {
                return true;
            }
        }
        return false;
    }



}

