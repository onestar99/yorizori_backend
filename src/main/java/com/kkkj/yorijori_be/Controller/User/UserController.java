package com.kkkj.yorijori_be.Controller.User;


import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Service.User.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {


    private UserService userService;

    @GetMapping("")
    @ResponseBody
    public String getUser(){


        userService.findAllUser();

        return "aa";
    }


    /*
    *
    *  front -> json 형식
    *
    * */

    @PostMapping("")
    @ResponseBody
    public void saveUser(UserDto userDto){
        userService.saveUser(userDto);
    }

}
