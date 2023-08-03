package com.kkkj.yorijori_be;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Base64;

//@Slf4j

@SpringBootTest
class YorijoriBeApplicationTests {

    private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    @Value("${oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${oauth2.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    @Value("${oauth2.jwt.secret-key}")
    private String jwtSecretKey;


    private String URI = "https://accounts.google.com/o/oauth2/v2/auth" +
            "?client_id=" + GOOGLE_CLIENT_ID +
            "&redirect_uri=" + GOOGLE_REDIRECT_URI +
            "&response_type=code" +
            "&scope=profile";

    @Test
    void contextLoads() {
        System.out.println(GOOGLE_CLIENT_ID);
        System.out.println(GOOGLE_CLIENT_SECRET);
        System.out.println(GOOGLE_REDIRECT_URI);
    }
    @Test
    void confirm() {
        URI = "https://accounts.google.com/o/oauth2/v2/auth" +
                "?client_id=" + GOOGLE_CLIENT_ID +
                "&redirect_uri=" + GOOGLE_REDIRECT_URI +
                "&response_type=code" +
                "&scope=profile";
        log.info(URI);
    }

    @Test
    void confirmJWT() {
        log.info("jwtSecretKey : " + jwtSecretKey);
    }

    @Test
    void generateSecretKey() {
        byte[] keyBytes = new byte[512 / 8]; // 비트를 바이트로 변환
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        String secretKey =  Base64.getEncoder().encodeToString(keyBytes); // Base64 인코딩하여 문자열로 반환
        log.info("secretKey : " + secretKey);
    }

}
