package com.kkkj.yorijori_be.Controller.User;


import com.kkkj.yorijori_be.Dto.User.UserDto;
import com.kkkj.yorijori_be.Repository.User.UserRepository;
import com.kkkj.yorijori_be.Service.User.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    private UserService userService;

    @GetMapping("")
    public String getUser(){
        return "aa";
    }


    @PostMapping("")
    public void saveUser(UserDto userDto){
        userService.saveUser(userDto);
    }

}
