package com.kkkj.yorijori_be;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

//@Slf4j

@SpringBootTest
class YorijoriBeApplicationTests {

    @Value("${oauth2.google.client-id}")
    private String GOOGLE_CLIENT_ID;
    @Value("${oauth2.google.client-secret}")
    private String GOOGLE_CLIENT_SECRET;
    @Value("${oauth2.google.redirect-uri}")
    private String GOOGLE_REDIRECT_URI;

    @Test
    void contextLoads() {
        System.out.println(GOOGLE_CLIENT_ID);
        System.out.println(GOOGLE_CLIENT_SECRET);
        System.out.println(GOOGLE_REDIRECT_URI);
    }

}
