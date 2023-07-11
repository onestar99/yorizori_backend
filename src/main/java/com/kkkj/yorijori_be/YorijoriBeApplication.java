package com.kkkj.yorijori_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class YorijoriBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(YorijoriBeApplication.class, args);
    }

}
