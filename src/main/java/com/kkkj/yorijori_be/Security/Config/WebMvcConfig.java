package com.kkkj.yorijori_be.Security.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {


    /*
    *
    * React Origin 요청 -> cors 허용
    *
    * */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://43.202.85.254", "https://43.202.85.254", "http://localhost:5000", "http://yori-zori.com", "https://yori-zori.com")
                .allowedMethods("OPTIONS","GET","POST","PUT","DELETE");
    }

}
