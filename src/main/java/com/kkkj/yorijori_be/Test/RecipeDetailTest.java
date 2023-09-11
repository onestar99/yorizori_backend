package com.kkkj.yorijori_be.Test;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class RecipeDetailTest {

    @RequestMapping("/hello")
    public String indexhel(){
        return "/hello.html";
    }

    @RequestMapping("/map")
    public String mapScript(){
        return "/mapTest.html";
    }


}
