package com.kkkj.yorijori_be.Cloud;

import com.amazonaws.services.s3.AmazonS3Client;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class S3Remover {


    private final AmazonS3Client amazonS3Client;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * S3에 업로드된 파일 삭제
     */
    public String deleteProfileImage(String imageAddress) {

        String result = "success";

        try {
            String keyName = imageAddress.split("https://yorizori-s3.s3.ap-northeast-2.amazonaws.com/")[1]; //   s3 절대주소 제거후 s3에서 삭제
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, keyName);
            if (isObjectExist) {
                amazonS3Client.deleteObject(bucket, keyName);
            } else {
                result = "file not found";
            }
        } catch (Exception e) {
            log.debug("Delete File failed", e);
        }

        return result;
    }
    /**
     * S3에 업로드된 파일 삭제
     */
    public String deleteFile(String imageAddress) {

        String result = "success";

        try {
            String keyName = imageAddress.substring(1); //     /로 DB에 저장되어 있기에 / 빼버리기.
            boolean isObjectExist = amazonS3Client.doesObjectExist(bucket, keyName);
            if (isObjectExist) {
                amazonS3Client.deleteObject(bucket, keyName);
            } else {
                result = "file not found";
            }
        } catch (Exception e) {
            log.debug("Delete File failed", e);
        }

        return result;
    }
}
